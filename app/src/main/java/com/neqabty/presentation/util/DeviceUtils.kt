package com.neqabty.presentation.util

import java.io.File

class DeviceUtils {

    fun isDeviceRooted(): Boolean {
        return isrooted() || findBinary("su")
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
}