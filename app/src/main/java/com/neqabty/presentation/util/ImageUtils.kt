package com.neqabty.presentation.util

import android.R
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Environment
import android.view.View
import android.widget.ImageView
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


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
        fun createImageFile(context: Context): File {
            // Create an image file name
            val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.ENGLISH).format(Date())
            val storageDir: File = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!
            return File.createTempFile(
                    "JPEG_${timeStamp}_", /* prefix */
                    ".jpg", /* suffix */
                    storageDir /* directory */
            )
//                    .apply {
//                // Save a file: path for use with ACTION_VIEW intents
//                currentPhotoPath = absolutePath
//            }
        }

//        @Throws(IOException::class)
//        fun createImageFile(dir: File, name: String): File {
//            val image = File.createTempFile(
//                    name, /* prefix */
//                    ".jpg", /* suffix */
//                    dir /* directory */
//            )
//            return image
//        }

        fun share(context: Context, path: String) {
//            val intent = Intent().apply {
//                this.action = Intent.ACTION_SEND
//                this.putExtra(Intent.EXTRA_STREAM, Uri.parse(path))
//                this.type = "image/*"
//            }
//            context.startActivity(Intent.createChooser(intent, context.resources.getText(R.string.sharee)))
        }

        fun getByteArrayFromImageView(imageView: ImageView): ByteArray? {
            try {
                val bitmapDrawable = imageView.getDrawable() as BitmapDrawable
                val bitmap: Bitmap
                val byteArr: ByteArray
                if (bitmapDrawable == null) {
                    imageView.buildDrawingCache()
                    bitmap = imageView.getDrawingCache()
                    imageView.buildDrawingCache(false)
                } else {
                    bitmap = bitmapDrawable.bitmap
                }
                val stream = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.JPEG, 20, stream)
                byteArr = stream.toByteArray()
                return byteArr
            }catch (e: Exception){
                return null
            }
        }

        fun getBitmapFromByteArray(byteArray: ByteArray): Bitmap? {
            try {
                val bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
                return bmp
            }catch (e: Exception){
                return null
            }
        }
    }
}
