package com.neqabty.yodawy.core.application

import android.app.Application
import com.neqabty.yodawy.core.utils.LocaleHelper
import dagger.hilt.android.HiltAndroidApp

class YodawyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        LocaleHelper().setLocale(this, "ar")
    }
}