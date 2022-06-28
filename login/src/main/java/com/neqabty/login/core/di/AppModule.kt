package com.neqabty.login.core.di


import android.app.Application
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.neqabty.login.core.utils.PreferencesHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class AppModule {

//    @Singleton
//    @Provides
//    fun provideSharedPreferences(application: Application): SharedPreferences {
//        return PreferenceManager.getDefaultSharedPreferences(application)
//    }
//
//    @Named("login")
//    @Provides
//    fun providePreferencesHelper(sharedPref: SharedPreferences): PreferencesHelper {
//        return PreferencesHelper(preferences = sharedPref)
//    }

}