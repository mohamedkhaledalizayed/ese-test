package com.neqabty.superneqabty.core.di

import android.app.Application
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.neqabty.superneqabty.core.utils.PreferencesHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
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