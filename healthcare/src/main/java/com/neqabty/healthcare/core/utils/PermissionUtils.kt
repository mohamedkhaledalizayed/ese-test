package com.neqabty.healthcare.core.utils

import android.Manifest
import android.content.Context
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener

object PermissionUtils {

    fun requestCameraPermission(context: Context, callback: PermissionsCallback) {
        Dexter.withContext(context)
            .withPermission(Manifest.permission.CAMERA)
            .withListener(object : PermissionListener {
                override fun onPermissionGranted(response: PermissionGrantedResponse?) {
                    // User has granted the permission
                    callback.onPermissionRequest(granted = true)
                }

                override fun onPermissionRationaleShouldBeShown(permission: PermissionRequest, token: PermissionToken) {
                    // User previously denied the permission, request them again
                    token.continuePermissionRequest()
                }

                override fun onPermissionDenied(response: PermissionDeniedResponse?) {
                    // User has denied the permission
                    callback.onPermissionRequest(granted = false)
                }
            }).check()
    }

}