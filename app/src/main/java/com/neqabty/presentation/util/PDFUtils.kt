package com.neqabty.presentation.util

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.net.Uri
import android.os.Environment
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileOutputStream


class PDFUtils {

    companion object {
        fun openPDF(context: Context, bytes: ByteArray) {
            var uri: Uri
            var file: File
            try {
                val fileName = "استمارة تحويل.pdf"
                val storageDir = context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)?.path
                file = File("$storageDir/$fileName")
                var os = FileOutputStream(file)
                os.write(bytes)
                os.close()

                uri = FileProvider.getUriForFile(
                    context,
                    "com.neqabty.fileprovider",
                    file
                )

                val intent = Intent(Intent.ACTION_VIEW)
                intent.setDataAndType(uri, "application/pdf")
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                val resInfoList: List<ResolveInfo> = context.getPackageManager()
                    .queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY)

                for (resolveInfo in resInfoList) {
                    val packageName = resolveInfo.activityInfo.packageName
                    context.grantUriPermission(
                        packageName,
                        uri,
                        Intent.FLAG_GRANT_WRITE_URI_PERMISSION or Intent.FLAG_GRANT_READ_URI_PERMISSION
                    )
                }
                context.startActivity(intent)
            } catch (e: ActivityNotFoundException) {
                println("*************************NO PDF**************************")
            }
        }
    }
}