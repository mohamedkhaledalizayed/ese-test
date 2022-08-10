package com.neqabty.presentation.util

import android.content.Context
import android.os.Build
import android.os.LocaleList
import java.util.*

class Config {
    companion object {
        var LANGUAGE: String = "en"
    }

    class ContextWrapper(base: Context) : android.content.ContextWrapper(base) {

        companion object {
            fun wrap(context: Context, newLocale: Locale): ContextWrapper {
                var context = context

                val res = context.resources
                val configuration = res.configuration

                context = when {
                    Build.VERSION.SDK_INT >= 24 -> {
                        configuration.setLocale(newLocale)

                        val localeList = LocaleList(newLocale)
                        LocaleList.setDefault(localeList)
                        configuration.setLocales(localeList)

                        context.createConfigurationContext(configuration)
                    }
                    else -> {
                        configuration.setLocale(newLocale)
                        context.createConfigurationContext(configuration)
                    }
                }

                return ContextWrapper(context)
            }
        }
    }
}