package com.neqabty

import android.content.Context
import android.content.res.Resources
import android.view.WindowManager
import androidx.multidex.MultiDexApplication
import com.neqabty.presentation.util.DisplayMetrics
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class MyApp : MultiDexApplication() {
    companion object {
        lateinit var appResources: Resources
    }

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        DisplayMetrics.setMetrics(getSystemService(Context.WINDOW_SERVICE) as WindowManager)
        appResources = resources
    }
}
