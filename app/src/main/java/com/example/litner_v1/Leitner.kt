package com.example.litner_v1

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class Leitner : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.leitner)




        //var dbHandler:DatabaseHandler? = null
        var dbHandler:DatabaseHandler = DatabaseHandler(this)
        var btn_save1:Button = findViewById(R.id.btn_save_id)
        var btn_show1:Button = findViewById(R.id.btn_show_id)
        var rec:RecyclerView = findViewById(R.id.rec_id)
        var btn_delete = findViewById<Button>(R.id.btn_delete_id)
        var btn_update = findViewById<Button>(R.id.btn_update_id)
        var edit_id = findViewById<EditText>(R.id.edit_Id_id)
        var edit_firstname1:EditText = findViewById(R.id.edit_firstname_id)
        var edit_lastname1:EditText = findViewById(R.id.edit_lastname_id)
        val btn_backMain = findViewById<Button>(R.id.btn_backMain_id)
        val btn_start= findViewById<Button>(R.id.btn_start_id)
        val user =Users()

        btn_backMain.setOnClickListener {

            val goToMain = Intent(this, MainActivity::class.java)
            startActivity(goToMain)
        }

        btn_start.setOnClickListener {

            val goToStart= Intent(this, Recycler_main::class.java)
            startActivity(goToStart)
        }


        //save on click
        btn_save1.setOnClickListener{

            user.firstName = edit_firstname1.text.toString()
            user.lastName = edit_lastname1.text.toString()

            var success = dbHandler.addUser(user)
            Toast.makeText(this,"result $success ", Toast.LENGTH_SHORT).show()
        }

        //show btn
        btn_show1.setOnClickListener{
            rec.layoutManager =LinearLayoutManager(this)
            var mylist = dbHandler.getAllUsersList()
            rec.adapter = RecyclerAdapter(mylist, this)

        }

        // delete

        btn_delete.setOnClickListener {

            var id:String = edit_id.text.toString()

            var success = dbHandler.deletUser(id.toInt())
            Toast.makeText(this,"result $success ", Toast.LENGTH_SHORT).show()
        }

        // update
        btn_update.setOnClickListener {

           // val user = Users()
            user.firstName = edit_firstname1.text.toString()
            user.lastName = edit_lastname1.text.toString()
            user.id = edit_id.text.toString().toInt()
            val a = dbHandler.updatUser(user)
            Toast.makeText(this,"result $a ", Toast.LENGTH_SHORT).show()

        }



    }
}