package com.neqabty.chefaa.core.application

import android.app.Application
import com.neqabty.yodawy.core.utils.LocaleHelper
import dagger.hilt.android.HiltAndroidApp

class ChefaaApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        LocaleHelper().setLocale(this, "ar")
    }
}