package com.aatmamartya.mandimitra

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.BitmapFactory
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import java.util.concurrent.TimeUnit


private const val NOTIFICATION_PERMISSION_REQUEST_CODE = 100

@SuppressLint("MissingPermission")
fun createGeneralReminderNotifications(context: Context, message: String) {
    val largeIcon = BitmapFactory.decodeResource(context.resources, R.drawable.final_app_icon_nobg)
    val builder = NotificationCompat.Builder(context, "general_notification_channel")
        .setSmallIcon(R.drawable.final_app_icon)
        .setLargeIcon(largeIcon)
        .setContentTitle("Mandi Mitra Reminder")
        .setContentText(message)
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)

    with(NotificationManagerCompat.from(context)) {
        notify(1, builder.build())
    }
}

fun schedulePeriodicReminders(context: Context) {
    Log.d(
        "ScheduleNotifications",
        "Scheduling notifications and setting notification_scheduled to true"
    )
    val sharedPreferences = context.getSharedPreferences("MandiMitraPrefs", Context.MODE_PRIVATE)
    sharedPreferences.edit().putBoolean("notifications_scheduled", true).apply()

    val periodicWorkRequest =
        PeriodicWorkRequest.Builder(ReminderWorker::class.java, 3, TimeUnit.HOURS)
            .build()

    WorkManager.getInstance(context).enqueue(periodicWorkRequest)
}

class ReminderWorker(appContext: Context, workerParams: WorkerParameters) :
    Worker(appContext, workerParams) {
    override fun doWork(): Result {
        val messages = arrayOf(
            "Sabji Mandi calling? Don't forget Mandi Mitra!",
            "Make your sabji shopping fun and efficient with Mandi Mitra.",
            "Guess who's making grocery shopping smarter? You with Mandi Mitra!",
            "Don't just buy sabji, buy it smartly with Mandi Mitra."
        )

        val message = messages[(messages.indices).random()]

        createGeneralReminderNotifications(applicationContext, message)

        return Result.success()
    }

}
