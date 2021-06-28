package com.example.litner_v1

import android.app.ActivityManager
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import kotlin.math.log

@Suppress("DEPRECATION")

class  MainActivity : AppCompatActivity() {

    var RecyclerMain= Recycler_main()
    var sharedpreferenc: SharedPreferences?= null


    var dbHandler:DatabaseHandler = DatabaseHandler(this)

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val serviceClass = TimeInBackgroundServices::class.java
        val intent = Intent(applicationContext, serviceClass)

        //open leitner
        val btn_gotoleitner = findViewById<Button>(R.id.btn_gotoleitner_id)
        btn_gotoleitner.setOnClickListener {

            val goToLeitner = Intent(this, Leitner::class.java)
            startActivity(goToLeitner)
        }

        //oprn menu leitner
        val btn_gotoMenuleitner = findViewById<Button>(R.id.btn_gotoMenuL_id)
        btn_gotoMenuleitner.setOnClickListener {

            val goTomenuleitner= Intent(this, Recycler_main::class.java)
            startActivity(goTomenuleitner)
        }

//open test
        val button_test = findViewById<Button>(R.id.btn_test)
        val button_test2 = findViewById<Button>(R.id.btn_test2)
        button_test.setOnClickListener{
            if (!isServiceRunning(serviceClass)) {
                startService(Intent(applicationContext, TimeInBackgroundServices::class.java))

            } else {
                toast("Service already running.")
            }
        }
        button_test2.setOnClickListener{
            dbHandler.resetReadyCard(2)
            //dbHandler.setReadyCard(1)
            dbHandler.setReadyCard(2)
            

           dbHandler.find_readycard(2)
/*
           dbHandler.updatStep(UsersStep(4,5,5,4))
            var ranknumber:Int = dbHandler.getRanknumber(4)
            toast("rank = $ranknumber")
            var capacity= dbHandler.getCapacity(4)
            toast("capacity = $capacity")
            var readycard= dbHandler.get_numbercard_ranknumber(4)
            toast("readycard = $readycard")
*/


        }
    }
    // Custom method to determine whether a service is running
    private fun isServiceRunning(serviceClass: Class<*>): Boolean {
        val activityManager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        // Loop through the running services
        for (service in activityManager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.name == service.service.className) {
                // If the service is running then return true
                return true
            }
        }
        return false
    }
}
// Extension function to show toast message
fun Context.toast(message:String){
    Toast.makeText(applicationContext,message,Toast.LENGTH_SHORT).show()
}

