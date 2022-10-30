package com.neqabty.shealth.core.application

import android.app.Application
import android.content.Context
import com.neqabty.shealth.core.utils.LocaleHelper
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class HealthCareApplication : Application() {
    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(LocaleHelper.onAttach(base, "ar"))
    }
}