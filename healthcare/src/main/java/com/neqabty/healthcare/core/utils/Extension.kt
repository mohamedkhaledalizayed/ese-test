package com.neqabty.healthcare.core.utils

import android.graphics.drawable.PictureDrawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.caverock.androidsvg.SVG

fun ImageView.loadSVG(svg: String) {
    val trimmedSVG = svg.replace("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\\n", "").replace('\"', '"')
    val svg = SVG.getFromString(trimmedSVG)
    val drawable = PictureDrawable(svg.renderToPicture())

    Glide.with(this)
        .load(drawable)
        .into(this)
}