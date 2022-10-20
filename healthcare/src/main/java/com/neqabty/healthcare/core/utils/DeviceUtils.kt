package com.neqabty.healthcare.core.utils

import android.os.Build
import java.io.File


class DeviceUtils {

    fun isDeviceRooted(): Boolean {
        return isrooted()  || findBinary("su")
    }

    private fun isrooted(): Boolean {
        val file = File("/system/app/Superuser.apk")
        return file.exists()
    }


    private fun isrooted2(): Boolean {
        return (canExecuteCommand("/system/xbin/which su")
                || canExecuteCommand("/system/bin/which su")
                || canExecuteCommand("which su"))
    }


    private fun canExecuteCommand(command: String): Boolean {
        var executedSuccesfully: Boolean
        try {
            Runtime.getRuntime().exec(command)
            executedSuccesfully = true
        } catch (e: Exception) {
            executedSuccesfully = false
        }

        return executedSuccesfully
    }

    fun findBinary(binaryName: String): Boolean {

        var found = false
        if (!found) {

            val places = arrayOf(
                "/sbin/",
                "/system/bin/",
                "/system/xbin/",
                "/data/local/xbin/",
                "/data/local/bin/",
                "/system/sd/xbin/",
                "/system/bin/failsafe/",
                "/data/local/"
            )
            for (where in places) {
                if (File(where + binaryName).exists()) {

                    found = true
                    break
                }
            }
        }
        return found
    }

    fun isProbablyAnEmulator() = Build.FINGERPRINT.startsWith("generic")
            || Build.FINGERPRINT.startsWith("unknown")
            || Build.MODEL.contains("google_sdk")
            || Build.MODEL.contains("Emulator")
            || Build.MODEL.contains("Android SDK built for x86")
            || Build.BOARD == "QC_Reference_Phone" //bluestacks
            || Build.MANUFACTURER.contains("Genymotion")
            || Build.HOST.startsWith("Build") //MSI App Player
            || (Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic"))
            || "google_sdk" == Build.PRODUCT
            || Build.HARDWARE.contains("goldfish")
            || Build.HARDWARE.contains("ranchu")
            || Build.MODEL.contains("google_sdk")
            || Build.PRODUCT.contains("sdk_google")
            || Build.PRODUCT.contains("sdk")
            || Build.PRODUCT.contains("sdk_x86")
            || Build.PRODUCT.contains("vbox86p")
            || Build.PRODUCT.contains("emulator")
            || Build.PRODUCT.contains("simulator")
}