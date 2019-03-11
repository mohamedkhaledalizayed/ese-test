package com.neqabty.presentation.util

import android.util.DisplayMetrics
import android.view.WindowManager

class DisplayMetrics(){
    companion object {
        var width : Int = 0
        var height : Int = 0

        fun setMetrics(windowManager: WindowManager){
            val displayMetrics = DisplayMetrics()
            windowManager.defaultDisplay.getMetrics(displayMetrics)
            width = displayMetrics.widthPixels
            height = displayMetrics.heightPixels
        }
    }

}
