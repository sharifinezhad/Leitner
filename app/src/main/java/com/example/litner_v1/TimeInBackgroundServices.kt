package com.example.litner_v1

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import java.util.*
import androidx.annotation.Nullable
import java.lang.Exception
import kotlin.collections.ArrayList


@Suppress("DEPRECATION")
class TimeInBackgroundServices: Service() {
    private lateinit var mHandler: Handler
    private lateinit var mRunnable: Runnable

    //////////////////not
    lateinit var notificationManager: NotificationManager
    lateinit var notificationChannel: NotificationChannel
    lateinit var builder: Notification.Builder
    var channelID = "com.notifi"
    var description = "this is notification"
    var user=Users()


    //////////////////////
    override fun onCreate() {
        //faghat yek bar in ejra mishavad
        //toast("yaser")
    }

    override fun onBind(intent: Intent): IBinder? {
        throw UnsupportedOperationException("Not yet implemented")
    }

    override fun onTaskRemoved(rootIntent: Intent) {
        val restartServiceIntent = Intent(applicationContext, this.javaClass)
        restartServiceIntent.setPackage(packageName)
        startService(restartServiceIntent)
        super.onTaskRemoved(rootIntent)
    }
    var a = 1
    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {

        onTaskRemoved(intent)

        find_leitner_time()


        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        toast("Service destroyed.")
        //mHandler.removeCallbacks(mRunnable)
    }

    private fun salen() {
        a += 1
        toast("Service started. $a")

    }

    //private fun find_leitner_time()


    private fun find_leitner_time() {

        //val user:Users=Users()
        var dbHandler: DatabaseHandler = DatabaseHandler(this)
        var last_index_ID = dbHandler.reciceTime()
        try {
        for (i in 1..last_index_ID) {

            if (System.currentTimeMillis() >= (dbHandler.getTime_Stamp(i))) {
                notifikation()
                dbHandler.setcolorBackground(dbHandler.getRanknumber(i))
                //dbHandler.setReadyCards(i,dbHandler.getRanknumber(i))
                dbHandler.setReadyCard(i)
                //toast(" ID $i")
                //toast(" rank ${dbHandler.getRanknumber(i)}")
            }
            else {
                //dbHandler.resetcolorBackground(dbHandler.getRanknumber(i))
                //dbHandler.setReadyCards(i,dbHandler.getRanknumber(i))
                dbHandler.resetReadyCard(i)
               // toast("nickst")
            }
        }
        }catch (e:Exception){
            toast(e.message.toString())
        }
    }

    fun notifikation(){
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        var intent = Intent(this, Recycler_main::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, 0)
/////////////
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            notificationChannel =
                NotificationChannel(channelID, description, NotificationManager.IMPORTANCE_DEFAULT)

            notificationManager.createNotificationChannel(notificationChannel)

            builder = Notification.Builder(this, channelID)
                .setContentTitle("new notifecation")
                ///////
                .setContentIntent(pendingIntent)
                ///////////
                .setContentText("yaser liked your new post ....")
                .setSmallIcon(R.mipmap.ic_launcher_icon)
        } else {
            //Toast.makeText(this, "api <26", Toast.LENGTH_LONG).show()
            builder = Notification.Builder(this)
                .setContentTitle("new notifecation")
                .setContentText("yaser liked your new post ....")
                .setSmallIcon(R.mipmap.ic_launcher_icon)
        }

        notificationManager.notify(0, builder.build())
    }
}