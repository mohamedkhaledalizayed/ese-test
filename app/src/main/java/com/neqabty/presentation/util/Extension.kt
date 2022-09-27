package com.neqabty.presentation.util

import android.content.Context
import android.content.Intent
import android.graphics.drawable.PictureDrawable
import android.net.Uri
import android.util.Base64
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.caverock.androidsvg.SVG

fun View.openMap(address: String, context: Context) {
    try {
        val uri = "geo:0,0?q=$address"
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
        context.startActivity(intent)
    } catch (e: Exception) {
        val uri = "http://maps.google.com/maps?q=$address"
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
        context.startActivity(intent)
    }
}

fun TextView.call(phone: String, context: Context) {
    val intent = Intent(Intent.ACTION_DIAL)
    intent.data = Uri.parse("tel:$phone")
    context.startActivity(intent)
}

fun <T> LiveData<T>.observeOnce(lifecycleOwner: LifecycleOwner, observer: Observer<T>) {
    observe(lifecycleOwner, object : Observer<T> {
        override fun onChanged(t: T?) {
            observer.onChanged(t)
            removeObserver(this)
        }
    })
}

fun ImageView.loadSVG(svg: String) {
    val trimmedSVG = svg.replace("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\\n", "").replace('\"', '"')
    val svg = SVG.getFromString(trimmedSVG)
    val drawable = PictureDrawable(svg.renderToPicture())

    Glide.with(this)
        .load(drawable)
        .into(this)
}

fun ImageView.loadString(img: String){
    val byteArray = android.util.Base64.decode(img, Base64.NO_WRAP)
    this.setImageBitmap(ImageUtils.getBitmapFromByteArray(byteArray!!))
}