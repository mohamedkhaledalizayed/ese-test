package com.neqabty.healthcare.core.utils

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Process
import android.util.Log
import java.io.BufferedReader
import java.io.File
import java.io.FileReader


class DeviceUtils {

    fun isEmulator(): Boolean {
        return (Build.FINGERPRINT.startsWith("generic")
                || Build.FINGERPRINT.startsWith("unknown")
                || Build.MODEL.contains("google_sdk")
                || Build.MODEL.contains("Emulator")
                || Build.MODEL.contains("Android SDK built for x86")
                || Build.BOARD == "QC_Reference_Phone" //bluestacks
                || Build.MANUFACTURER.contains("Genymotion")
                || Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic")
                || "google_sdk" == Build.PRODUCT
                || Build.HARDWARE.contains("goldfish")
                || Build.HARDWARE.contains("ranchu")
                || Build.MODEL.contains("google_sdk")
                || Build.PRODUCT.contains("sdk_google")
                || Build.PRODUCT.contains("sdk")
                || Build.PRODUCT.contains("sdk_x86")
                || Build.PRODUCT.contains("vbox86p")
                || Build.PRODUCT.contains("emulator")
                || Build.PRODUCT.contains("simulator"))
    }

    fun isDeviceRooted(context: Context): Boolean {
        return isrooted()  || findBinary("su") || checkHooking(context) || checkHooking() ||
                detectSuspiciousSharedObjects()  || isEmulator()
    }

    private fun isrooted(): Boolean {
        val file = File("/system/app/Superuser.apk")
        return file.exists()
    }


    private fun isrooted2(): Boolean {
        return (canExecuteCommand("/system/xbin/which su") ||
                canExecuteCommand("/system/bin/which su") ||
                canExecuteCommand("which su"))
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

    private fun findBinary(binaryName: String): Boolean {

        var found = false

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

        return found
    }

    private fun checkHooking(context: Context): Boolean{
        val packageManager: PackageManager = context.packageManager
        val applicationInfoList = packageManager.getInstalledApplications(PackageManager.GET_META_DATA)

        for (applicationInfo in applicationInfoList) {
            if (applicationInfo.packageName.equals("de.robv.android.xposed.installer")) {
                return true
            }
            if (applicationInfo.packageName.equals("com.saurik.substrate")) {
                return true
            }
        }
        return false
    }

    private fun checkHooking(): Boolean{
        try {
            throw java.lang.Exception("hooking")
        } catch (e: java.lang.Exception) {
            var zygoteInitCallCount = 0
            for (stackTraceElement in e.stackTrace) {
                if (stackTraceElement.className == "com.android.internal.os.ZygoteInit") {
                    zygoteInitCallCount++
                    if (zygoteInitCallCount == 2) {
                        return true
                    }
                }
                if (stackTraceElement.className == "com.saurik.substrate.MS$2" && stackTraceElement.methodName == "invoked") {
                    return true
                }
                if (stackTraceElement.className == "de.robv.android.xposed.XposedBridge" && stackTraceElement.methodName == "main") {
                    return true
                }
                if (stackTraceElement.className == "de.robv.android.xposed.XposedBridge" && stackTraceElement.methodName == "handleHookedMethod") {
                    return true
                }
            }
        }

        return false
    }

    private fun detectSuspiciousSharedObjects(): Boolean{
        try {
            val libraries: MutableSet<String> = HashSet<String>()
            val mapsFilename = "/proc/" + Process.myPid() + "/maps"
            val reader = BufferedReader(FileReader(mapsFilename))
            var line: String
            while (reader.readLine().also { line = it } != null) {
                if (line.endsWith(".so") || line.endsWith(".jar")) {
                    val n = line.lastIndexOf(" ")
                    libraries.add(line.substring(n + 1))
                }
            }
            for (library in libraries) {
                if (library.contains("com.saurik.substrate")) {
                    return true
                }
                if (library.contains("XposedBridge.jar")) {
                    return true
                }
            }
            reader.close()
        } catch (e: java.lang.Exception) {
            Log.wtf("HookDetection", e.toString())
        }
        return false
    }
}