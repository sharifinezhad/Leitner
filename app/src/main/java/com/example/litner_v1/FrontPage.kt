package com.example.litner_v1

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import org.w3c.dom.Text
import java.lang.Exception
var position1 = 0
var datapointer = "0"
class FrontPage: AppCompatActivity() {
    var sharedpreferenc: SharedPreferences?= null
    var dbHandler:DatabaseHandler = DatabaseHandler(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.front_page)

        val user = Users()

        val btn_rotate = findViewById<Button>(R.id.btn_rotate_id)
        val btn_kurect = findViewById<Button>(R.id.btn_kurect_id)
        val btn_backleitner = findViewById<Button>(R.id.btn_backtoleitner_id)
        val btn_fals = findViewById<Button>(R.id.btn_false_id)
        val txt_front= findViewById<TextView>(R.id.txt_front_id)
        val txt_frontup= findViewById<TextView>(R.id.txt_frontup_id)



        try {
            val  positionCard= dbHandler.get_ID_WithRank(position1)   //mit position
            Toast.makeText(this,"ok ${position1}",Toast.LENGTH_SHORT).show()
            txt_front.text= dbHandler.getname(positionCard.get(0).id)  //first show







        //btn rotate

        var rotate1 = 3
        user.listID=0
        //user.datapointer = "1"
        btn_rotate.setOnClickListener {
            try {
                datapointer = positionCard.get(user.listID).id.toString()
            if (rotate1==3){
                txt_frontup.text= "پاسخ"
                txt_front.text= dbHandler.getlastname(datapointer)
                rotate1= 7
            } else {
                txt_frontup.text= "پرسش"
                txt_front.text= dbHandler.getname(datapointer.toInt())
                rotate1= 3
            }
            }catch (e:Exception){
               txt_front.text="خالی"
                changcolor_rank()
                //dbHandler.resetcolorBackground(dbHandler.getRanknumber(datapointer.toInt()-1))
            }
        }
        //btn kurect
        btn_kurect.setOnClickListener {
         try {

             datapointer = positionCard.get(user.listID).id.toString()
             //toast("id = $datapointer")
             var ranknumber:Int = dbHandler.getRanknumber(datapointer.toInt())
             //toast("rank = $ranknumber")
             var capacity= dbHandler.getCapacity(ranknumber+2)
             //toast("capacity = $capacity")
             var readycard= dbHandler.get_numbercard_ranknumber(ranknumber+1)
             //toast("readycard = $readycard")
             if ((capacity-readycard) > 0) {



             //Toast.makeText(this,"${datapointer} ", Toast.LENGTH_SHORT).show()
             dbHandler.updatToUpRankfun(datapointer)
             dbHandler.resetReadyCard(datapointer.toInt())
             dbHandler.dec_ReadyCards(datapointer.toInt(),dbHandler.getreadycards(datapointer.toInt()))
            //Toast.makeText(this,"result ${datapointer} ", Toast.LENGTH_SHORT).show()


            txt_frontup.text= "پرسش"
            txt_front.text= dbHandler.getname(datapointer.toInt()+1)
             user.listID++
             } else{
                 Toast.makeText(this, "مرحله تکمیل است", Toast.LENGTH_SHORT).show()

             }

            //toast("rank = ${dbHandler.getRanknumber(datapointer.toInt())}")
         }catch (e:Exception){
             txt_front.text="پایان"
             changcolor_rank()

             //dbHandler.resetcolorBackground(dbHandler.getRanknumber(datapointer.toInt()-1))
             //toast("rank = ${dbHandler.getRanknumber(datapointer.toInt())}")
         }

        }
        // btn false
        btn_fals.setOnClickListener {
            try {

                datapointer = positionCard.get(user.listID).id.toString()
                user.listID++
            dbHandler.resetRanknumber(datapointer)
                dbHandler.resetReadyCard(datapointer.toInt())
                dbHandler.setReadyCards(datapointer.toInt(),dbHandler.getRanknumber(datapointer.toInt()))
            //Toast.makeText(this,"result ${datapointer} ", Toast.LENGTH_SHORT).show()


            txt_frontup.text= "پرسش"
            txt_front.text= dbHandler.getname(datapointer.toInt()+1)
            }catch (e:Exception){
                txt_front.text="پایان"
                changcolor_rank()
                //dbHandler.resetcolorBackground(dbHandler.getRanknumber(datapointer.toInt()-1))

            }

        }
            // not exist card
        }catch (e:Exception){
            txt_front.text="خالی"
            //dbHandler.resetcolorBackground()
            changcolor_rank()
        // dbHandler.resetcolorBackground(dbHandler.getRanknumber(datapointer.toInt()-1))
        }
        //btn_backleitner
        btn_backleitner.setOnClickListener {
            val backToleitner = Intent(this, Recycler_main::class.java)
            startActivity(backToleitner)
        }


    }
    fun changcolor_rank(){
        sharedpreferenc = getSharedPreferences("level",Context.MODE_PRIVATE)
        var step1= sharedpreferenc?.getString("level","")!!.toInt()
        for (i in 1..step1) {
            if (dbHandler.find_readycard(i) == 0) {
                dbHandler.resetcolorBackground(i)
            }else{dbHandler.setcolorBackground(i)}
        }
    }
}