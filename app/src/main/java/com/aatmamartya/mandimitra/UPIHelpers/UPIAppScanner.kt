package com.aatmamartya.mandimitra.UPIHelpers

import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.net.toUri


object UPIAppScanner {
    fun checkInstalledUPIApps(
        context: Context,
        upiAppsToCheck: List<String>
    ): Map<String, Boolean> {
        val packageManager = context.packageManager

        Log.d("checkInstalledUPIApps", "Function called")
        val installedApps = packageManager.getInstalledApplications(PackageManager.GET_META_DATA)
        Log.d("checkInstalledUPIApps", "Number of installed apps: ${installedApps.size}")
        Log.d("checkInstalledUPIApps", "Installed apps: $installedApps")

        // Filter out system apps
        val nonSystemApps = installedApps.filterNot { appInfo ->
            val packageInfo = packageManager.getPackageInfo(appInfo.packageName, 0)
            isSystemPackage(packageInfo)
        }

        val installedAppsMap = nonSystemApps.associateBy { it.packageName }
        Log.d("checkInstalledUPIApps", "Installed non-system apps map: ${installedAppsMap.size}")
        Log.d("checkInstalledUPIApps", "Installed non-system apps: $installedAppsMap")
        val result = upiAppsToCheck.associateWith { installedAppsMap.containsKey(it) }
        Log.d("checkInstalledUPIApps", "Result: $result")
        return result
    }

    /**
     * Return whether the given PackageInfo represents a system package or not.
     * User-installed packages (Market or otherwise) should not be denoted as
     * system packages.
     *
     * @param pkgInfo
     * @return
     */
    private fun isSystemPackage(pkgInfo: PackageInfo): Boolean {
        return pkgInfo.applicationInfo!!.flags and ApplicationInfo.FLAG_SYSTEM != 0
    }

    fun openUPIApp(context: Context, packageName: String) {

        val packageManager = context.packageManager
//        val packages: List<ApplicationInfo> =
//            packageManager.getInstalledApplications(PackageManager.GET_META_DATA)

        try {
            val intent = packageManager.getLaunchIntentForPackage(packageName)
                ?: throw PackageManager.NameNotFoundException()
            intent.addCategory(Intent.CATEGORY_LAUNCHER)
            Log.d("UPIAppIntent", intent.toString())
            context.startActivity(intent)
        } catch (e: PackageManager.NameNotFoundException) {
            Log.e("openUPIApp", "No UPI app found with package name $packageName")
            val playStoreIntent = Intent(
                Intent.ACTION_VIEW,
                "https://play.google.com/store/apps/details?id=$packageName".toUri()
            )
            context.startActivity(playStoreIntent)
        }
    }
}