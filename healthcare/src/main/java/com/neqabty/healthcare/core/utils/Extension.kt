package com.neqabty.healthcare.core.utils

import android.graphics.drawable.PictureDrawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.caverock.androidsvg.SVG
import java.util.regex.Pattern

fun ImageView.loadSVG(svg: String) {
    val trimmedSVG = svg.replace("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\\n", "").replace('\"', '"')
    val svg = SVG.getFromString(trimmedSVG)
    val drawable = PictureDrawable(svg.renderToPicture())

    Glide.with(this)
        .load(drawable)
        .into(this)
}

fun String.isMobileValid(): Boolean {
    if(Pattern.matches("(011|012|010|015)[0-9]{8}", this)) {
        return true
    }
    return false
}

fun String.isNationalIdValid(): Boolean {
    if(Pattern.matches("(2|3)[0-9][0-9][0-1][0-9][0-3][0-9](01|02|03|04|05|11|12|13|14|15|16|17|18|19|21|22|23|24|25|26|27|28|29|31|32|33|34|35|88)\\d\\d\\d\\d\\d", this)) {
        return true
    }
    return false
}

var emailPattern: String = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$"

fun String.isValidEmail() = Pattern.compile(emailPattern, Pattern.CASE_INSENSITIVE).matcher(this).matches()
