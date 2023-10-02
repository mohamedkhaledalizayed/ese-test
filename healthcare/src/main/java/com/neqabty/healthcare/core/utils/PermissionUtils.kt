package com.neqabty.healthcare.core.utils

import android.Manifest
import android.content.Context
import android.widget.Toast
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
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

    fun requestLocationPermissions(context: Context, callback: PermissionsCallback) {
        Dexter.withContext(context)
            .withPermissions(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
            .withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(multiplePermissionsReport: MultiplePermissionsReport) {
                    if (multiplePermissionsReport.areAllPermissionsGranted()) {
                        callback.onPermissionRequest(granted = true)
                    }
                    if (multiplePermissionsReport.isAnyPermissionPermanentlyDenied) {
                        // permission is denied permanently, we will show user a dialog message.
                        callback.onPermissionRequest(granted = false)
                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    list: List<PermissionRequest?>?,
                    permissionToken: PermissionToken
                ) {
                    // this method is called when user grants some permission and denies some of them.
                    permissionToken.continuePermissionRequest()
                }
            }).withErrorListener {
                Toast.makeText(context, "Error occurred! ", Toast.LENGTH_SHORT).show()
            }.check()
    }

}