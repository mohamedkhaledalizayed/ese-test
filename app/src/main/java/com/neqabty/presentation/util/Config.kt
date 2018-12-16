package com.neqabty.presentation.util

import android.content.Context
import android.os.Build
import java.util.*



class Config (){
    companion object {
        val LANGUAGE : String = "ar"
        val API_KEY : String = "62517db39eef319ca9749d78ffa28fac9b54307fddc1c60dcb532a6514a7b5b1"

        fun setLocale(lang : String,context :Context){
            val res = context.resources
            val dm = res.displayMetrics
            val config = res.configuration

            if (Build.VERSION.SDK_INT >= 17) {
                config.setLocale(Locale(lang.toLowerCase()))
            }else{
                config.locale = Locale(lang.toLowerCase())
            }
            res.updateConfiguration(config, dm)
        }
    }
}