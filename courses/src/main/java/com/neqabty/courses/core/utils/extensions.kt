package com.neqabty.courses.core.utils

import android.os.Build
import android.text.Html
import android.widget.TextView


fun TextView.setHtmlText(htmlText: String) {
    this.text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(htmlText, Html.FROM_HTML_MODE_COMPACT)
    } else {
        Html.fromHtml(htmlText)
    }
}