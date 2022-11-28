package com.neqabty.healthcare.core.di

import android.app.Application
import com.neqabty.healthcare.core.data.Constants.ENCRYPT_KEY
import com.neqabty.healthcare.core.data.Constants.PREFS_FILE
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
    fun provideSharedPreferences(application: Application): com.securepreferences.SecurePreferences {
        return com.securepreferences.SecurePreferences(application, ENCRYPT_KEY, PREFS_FILE)
    }

    @Singleton
    @Provides
    fun providePreferencesHelper(sharedPref: com.securepreferences.SecurePreferences): PreferencesHelper {
        return PreferencesHelper(preferences = sharedPref)
    }

}