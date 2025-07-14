package com.aatmamartya.mandimitra

import android.Manifest
import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.aatmamartya.mandimitra.UPIHelpers.UPIAppScanner
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Locale


private const val NOTIFICATION_PERMISSION_REQUEST_CODE = 100

fun getLanguagePreference(context: Context): String {
    val sharedPreferences =
        context.getSharedPreferences(PreferencesKeys.PREFERENCES_FILE_KEY, Context.MODE_PRIVATE)
    return sharedPreferences.getString(PreferencesKeys.LANGUAGE_PREFERENCE_KEY, "en") ?: "en"
}

fun setLocale(activity: Activity, languageCode: String) {
    val locale = Locale(languageCode)
    Locale.setDefault(locale)
    val config = activity.resources.configuration
    config.setLocale(locale)
    activity.resources.updateConfiguration(config, activity.resources.displayMetrics)

    // Optional: Restart the current activity to apply language changes immediately
//    activity.recreate()
}

class MainActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val upiAppsToCheck = listOf(
            "net.one97.paytm",
            "com.google.android.apps.nbu.paisa.user",
            "com.dreamplug.androidapp",
            "com.phonepe.app"
        )

        val installedUPIApps = UPIAppScanner.checkInstalledUPIApps(this, upiAppsToCheck)
        Log.d("installedUPIApps", installedUPIApps.toString())

        // Apply stored language preference
        val currentLanguage = getLanguagePreference(this)
        setLocale(this, currentLanguage)
        setContentView(R.layout.activity_main)

        // creating the "General Reminders" notification channel
        createGeneralNotificationChannel()

        // Checking for notification permission and scheduling periodic reminders
        checkPermissionCreateAndScheduleNotifications()

        Log.d("MainActivityLV", "Launching coroutine")
        CoroutineScope(Dispatchers.Main).launch {
        }

    }

    private fun checkPermissionCreateAndScheduleNotifications() {
        val sharedPreferences = getSharedPreferences("MandiMitraPrefs", Context.MODE_PRIVATE)
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // Requesting the permission
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                NOTIFICATION_PERMISSION_REQUEST_CODE
            )
        } else {
            // Permission already granted
            // if notification not scheduled, then schedule them, else skip
            val notificationsScheduled =
                sharedPreferences.getBoolean("notifications_scheduled", false)
            if (!notificationsScheduled) {
                Log.d(
                    "ScheduleNotifications",
                    "Scheduling notifications notifications_scheduled is false"
                )
                schedulePeriodicReminders(this)
            } else {
                Log.d(
                    "ScheduleNotifications",
                    "Notifications already scheduled. notifications_scheduled = $notificationsScheduled"
                )
            }

        }
    }

    private fun createGeneralNotificationChannel() {
        // setting up notification channel
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "General Reminders"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val descriptionText = "General reminders to use the app next time you go shopping"
            val channel = NotificationChannel("general_notification_channel", name, importance)
                .apply { description = descriptionText }

            // Registering the chanel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == NOTIFICATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, retrying notification builder
                schedulePeriodicReminders(this)
            } else {
                Toast
                    .makeText(this, "Notification Permission denied", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }
}