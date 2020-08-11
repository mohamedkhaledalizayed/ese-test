package com.neqabty.presentation.ui.common

import android.content.Context
import android.graphics.PorterDuff
import android.os.Build
import android.databinding.adapters.CompoundButtonBindingAdapter.setChecked
import android.graphics.Color
import android.util.AttributeSet
import android.widget.Switch

class CustomSwitch : Switch {

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {}

    override fun setChecked(checked: Boolean) {
        super.setChecked(checked)
        changeColor(checked)
    }

    private fun changeColor(isChecked: Boolean) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            val thumbColor: Int
            val trackColor: Int

            if (isChecked) {
                thumbColor = Color.parseColor("#1d1ad2")
                trackColor = thumbColor
            } else {
                thumbColor = Color.parseColor("grey")
                trackColor = thumbColor
            }

            try {
                thumbDrawable.setColorFilter(thumbColor, PorterDuff.Mode.MULTIPLY)
                trackDrawable.setColorFilter(trackColor, PorterDuff.Mode.MULTIPLY)
            } catch (e: NullPointerException) {
                e.printStackTrace()
            }
        }
    }
}