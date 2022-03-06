package com.example.workmanagerservice.service

import android.app.*
import android.content.Context
import android.content.Intent
import android.nfc.Tag
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.ListenableWorker.Result.*
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.workmanagerservice.MainActivity
import com.example.workmanagerservice.R
import com.example.workmanagerservice.database.AppDatabase
import com.example.workmanagerservice.entity.User

class MyWork(var context: Context, workerParameters: WorkerParameters) : Worker(context, workerParameters){
    private val TAG = "MyWorker"

    companion object{
        const val CHANNEL_ID = "channel_id"
        const val NOTIFICATION_ID = 1
    }

    override fun doWork(): Result {
       // showNotification()
        addFun()
        return success()
    }

    private fun addFun(){
        AppDatabase.getInstance(context).newDao().addUser(User())
    }

/*    private fun showNotification() {
        Log.d(TAG, "loadItem: ")
        val intent = Intent(applicationContext, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            applicationContext,
        )
        val notification = NotificationCompat.Builder(
            applicationContext,
            CHANNEL_ID
        )
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle("New Task")
            .setContentText("New Notification")
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channelName = "Channel name"
            val channelDescription = "Channel description"
            val channelImportance = NotificationManager.IMPORTANCE_HIGH

            val channel = NotificationChannel(CHANNEL_ID, channelName, channelImportance).apply {
                description = channelDescription
            }
            val notificationManager = applicationContext.getSystemService(
                Context.NOTIFICATION_SERVICE
            ) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
        with(NotificationManagerCompat.from(applicationContext)){
            notify(NOTIFICATION_ID,notification.build())
        }
    }*/
}