package com.neqabty

import android.app.Activity
import android.app.Application
import android.content.Context
import android.view.WindowManager
import com.neqabty.presentation.di.AppInjector
import com.neqabty.presentation.util.Config
import com.neqabty.presentation.util.DisplayMetrics
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import timber.log.Timber
import javax.inject.Inject


class MyApp : Application(), HasActivityInjector {
    @Inject//AndroidInjection.inject() gets a DispatchingAndroidInjector<Activity> from the Application and passes your activity to inject(Activity).
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        AppInjector.init(this)
        Config.setLocale(Config.LANGUAGE, this)
        DisplayMetrics.setMetrics(getSystemService(Context.WINDOW_SERVICE) as WindowManager)
    }

    override fun activityInjector() = dispatchingAndroidInjector
}
