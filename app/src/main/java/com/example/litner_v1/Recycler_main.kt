package com.example.litner_v1


import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler as Recycler

var step = 1
      var time =1
      var capacity = 7
      var levels = 1
      var check=1

@Suppress("DEPRECATION")
class Recycler_main() : AppCompatActivity() {

    var card: ArrayList<Rec_Menu> = ArrayList()
    val user =Users()
    val userstep= UsersStep()
    var dbHandler:DatabaseHandler = DatabaseHandler(this)
    var sharedpreferenc: SharedPreferences?= null

    constructor(parcel: Parcel) : this() {
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.recycler_main)

        sharedpreferenc = getSharedPreferences("level",Context.MODE_PRIVATE)
        var recycler_main= findViewById<RecyclerView>(R.id.card_id)

         //Exception modiriat khata
        try {
        loaddata()
        }catch (e:Exception){
            savedata()
        }
        step= 1
        time= 1
        //////////
        card.add(Rec_Menu("مرحله $step", "کارتهای آماده${dbHandler.get_numbercard_ranknumber(
            step-1)} از ${dbHandler.get_numbercard_ranknumber(
            step-1)}",
            "گنجایش : نامحدود", "زمان مرور:آزاد"))
        ++step
        --levels
        ///////////
        while (levels != 0) {

            card.add(Rec_Menu("مرحله $step", "کارتهای آماده${dbHandler.find_readycard(
                step-1)} از ${dbHandler.get_numbercard_ranknumber(
                step-1)}",
                "گنجایش $capacity", "زمان مرور $time روز"))

            dbHandler.updatStep(UsersStep(step, capacity,dbHandler.get_numbercard_ranknumber(
                step-1), time))
            ++step
            --levels
            time *= 2
            capacity *= 2
            //recycler_main.setBackgroundColor(0xff60a0e0.toInt())
        }

        var recycler_list = findViewById<RecyclerView>(R.id.rec_main_id)
        recycler_list.layoutManager = LinearLayoutManager(this)
        recycler_list.adapter = RecyclerMainAdapter(card, this)

        //btn back r to main
        var btn_bacRtoM_id: Button = findViewById(R.id.btn_bacRtoM_id)
        btn_bacRtoM_id.setOnClickListener {

            val backToleitner = Intent(this, MainActivity::class.java)
            startActivity(backToleitner)
        }
        //btn seting
        var seting: Button = findViewById(R.id.btn_seting_id)
        seting.setOnClickListener {

            //*//val progressDialog = ProgressDialog(this)
            //*//progressDialog.setMessage("yaser")
            //*//progressDialog.setCancelable(false)
            //*//progressDialog.show()
            val builder = AlertDialog.Builder(this)
            val dialogView = layoutInflater.inflate(R.layout.alertdialog, null)
            val etxt_step = dialogView.findViewById<EditText>(R.id.etxt_step_id)
            val etxt_cardnumber = dialogView.findViewById<EditText>(R.id.cardnumber_id)
            val btn_stepok = dialogView.findViewById<Button>(R.id.btn_stepok_id)
            val btn_stepcancel = dialogView.findViewById<Button>(R.id.btn_stepcancel_id)
            builder.setView(dialogView)
            builder.setCancelable(true)
            val dialog = builder.create()
            dialog.show()

            //*//Handler().postDelayed({dialog.dismiss()},5000)
            //btn step cancel
            btn_stepcancel.setOnClickListener {
                dialog.cancel()
            }
            //btn step ok
            btn_stepok.setOnClickListener {

                dialog.cancel()
                //Toast.makeText(this, "ok... ", Toast.LENGTH_SHORT).show()
                levels = etxt_step.text.toString().toInt()
                capacity = etxt_cardnumber.text.toString().toInt()
                check = 5
                savedata()

                step= 1
                time= 1
                //var level = userstep.levels
                card.clear()
                card.add(Rec_Menu("مرحله $step", "کارتهای آماده${dbHandler.get_numbercard_ranknumber(
                    step-1)} از ${dbHandler.get_numbercard_ranknumber(
                    step-1)}",
                    "گنجایش :  نامحدود", "زمان مرور:آزاد"))
                ++step
                --levels
                //time *= 2
                //capacity *= 2
                while (levels != 0) {
                    card.add(Rec_Menu("مرحله $step", "کارتهای آماده${dbHandler.find_readycard(
                        step-1)} از ${dbHandler.getRanknumber(
                        step-1)}",
                            "گنجایش $capacity", "زمان مرور $time روز"))
                    dbHandler.updatStep(UsersStep(step, capacity,dbHandler.get_numbercard_ranknumber(
                        step-1), time))
                    ++step
                    --levels
                    time *= 2
                    capacity *= 2

                }
                var recycler_list = findViewById<RecyclerView>(R.id.rec_main_id)
                recycler_list.layoutManager = LinearLayoutManager(this)
                recycler_list.adapter = RecyclerMainAdapter(card, this)
            }

        }

    }

     fun savedata(){
        val editor=sharedpreferenc?.edit()
        editor?.putString("level",levels.toString())
        editor?.putString("check",check.toString())
        editor?.putString("cardnumber",capacity.toString())
        editor?.apply()
        //Toast.makeText(this,"$levels + $check +$capacity", Toast.LENGTH_SHORT).show()
    }

     fun loaddata(){

        levels= sharedpreferenc?.getString("level","")!!.toInt()
        check= sharedpreferenc?.getString("check","")!!.toInt()
        capacity= sharedpreferenc?.getString("cardnumber","")!!.toInt()
    }

    companion object CREATOR : Parcelable.Creator<Recycler_main> {
        override fun createFromParcel(parcel: Parcel): Recycler_main {
            return Recycler_main(parcel)
        }

        override fun newArray(size: Int): Array<Recycler_main?> {
            return arrayOfNulls(size)
        }
    }

}
