package com.neqabty.presentation.binding

import android.databinding.BindingAdapter
import android.graphics.Typeface
import android.view.View
import android.widget.TextView

/**
 * Data Binding adapters specific to the app.
 */
object BindingAdapters {
    @JvmStatic
    @BindingAdapter("visibleGone")
    fun showHide(view: View, show: Boolean) {
        view.visibility = if (show) View.VISIBLE else View.GONE
    }

    @JvmStatic
    @BindingAdapter("android:typeface")
    fun setTypeface(textView: TextView, isRead: Boolean) {
        textView.typeface = if (isRead) Typeface.DEFAULT else Typeface.DEFAULT_BOLD
    }
}
