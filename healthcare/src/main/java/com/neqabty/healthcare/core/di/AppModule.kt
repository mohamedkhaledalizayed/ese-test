package com.neqabty.healthcare.core.di

import android.app.Application
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.neqabty.healthcare.core.data.PreferencesHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {


    @Singleton
    @Provides
    fun provideSharedPreferences(application: Application): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(application)
    }

    @Singleton
    @Provides
    fun providePreferencesHelper(sharedPref: SharedPreferences): PreferencesHelper {
        return PreferencesHelper(preferences = sharedPref)
    }

}