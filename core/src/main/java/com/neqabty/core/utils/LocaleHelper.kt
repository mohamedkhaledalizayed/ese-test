package com.neqabty.core.utils

import android.content.Context
import android.content.res.Configuration
import android.os.Build
import java.util.*

class LocaleHelper {


    fun setLocale(context: Context?,lang: String) {
        val locale = Locale(lang)

        Locale.setDefault(locale)


        val resources = context?.resources
        val configuration = resources?.configuration
        val displayMetrics = resources?.displayMetrics
        configuration?.setLocale(locale)
        resources?.updateConfiguration(configuration, displayMetrics)

    }
}
