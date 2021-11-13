package com.neqabty.yodawy.core.application

import android.app.Application
import com.neqabty.yodawy.core.RetrofitModule.Companion.initialize
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class YodawyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initialize(this)
    }
}