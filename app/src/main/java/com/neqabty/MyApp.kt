package com.neqabty

import android.app.Activity
import android.app.Application
import android.content.Context
import android.view.WindowManager
import com.neqabty.presentation.di.AppInjector
import com.neqabty.presentation.util.DisplayMetrics
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import timber.log.Timber
import javax.inject.Inject

class MyApp : Application(), HasAndroidInjector {
    @Inject // AndroidInjection.inject() gets a DispatchingAndroidInjector<Activity> from the Application and passes your activity to inject(Activity).
    lateinit var androidInjector : DispatchingAndroidInjector<Any>
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        AppInjector.init(this)
        DisplayMetrics.setMetrics(getSystemService(Context.WINDOW_SERVICE) as WindowManager)
    }
    override fun androidInjector(): AndroidInjector<Any> {
        return androidInjector
    }
}
