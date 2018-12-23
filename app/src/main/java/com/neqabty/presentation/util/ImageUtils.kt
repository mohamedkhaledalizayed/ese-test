package com.neqabty.presentation.util

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.Uri
import android.view.View
import com.neqabty.R
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class ImageUtils {

    companion object {

        fun captureScreenshot(view: View, path: String, name: String): File {
            val bitmap = loadBitmapFromView(view, view.width, view.height)
            return ImageUtils.saveImage(bitmap, name + ".jpg", path)
        }


//        fun captureScreenshot(view: View , path :String , name :String) : File?{
//            var file : File? = null
//            if(view.width != null && view.height != null) {
//                if(view.width != 0 && view.height != 0) {
//                    val bitmap = loadBitmapFromView(view, view.width, view.height)
//                    file = ImageUtils.saveImage(bitmap, name + ".jpg", path)
//                }
//            }
//            return file
//        }
//

        fun loadBitmapFromView(v: View, width: Int, height: Int): Bitmap {
            val b = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
            val c = Canvas(b)
            v.layout(0, 0, v.layoutParams.width, v.layoutParams.height)
            v.draw(c)
            return b
        }

        fun saveImage(bitmap: Bitmap, name: String, path: String): File {
            val myDir = File(path)
            myDir.mkdirs()
            val file = File(myDir, name)
            if (file.exists())
                file.delete()
            try {
                val out = FileOutputStream(file)
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)
                out.flush()
                out.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return file
        }


        @Throws(IOException::class)
        fun createImageFile(dir: File, name: String): File {
            val image = File.createTempFile(
                    name, /* prefix */
                    ".jpg", /* suffix */
                    dir      /* directory */
            )
            return image
        }


        fun share(context: Context, path: String) {
            val intent = Intent().apply {
                this.action = Intent.ACTION_SEND
                this.putExtra(Intent.EXTRA_STREAM, Uri.parse(path))
                this.type = "image/*"
            }
            context.startActivity(Intent.createChooser(intent, context.resources.getText(R.string.sharee)))
        }

    }

}
