package com.neqabty.login.core.di

import android.app.Application
import android.content.SharedPreferences
import com.neqabty.login.core.data.Constants.ENCRYPT_KEY
import com.neqabty.login.core.data.Constants.PREFS_FILE
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

    @Provides
    fun provideSharedPreferences(application: Application): SharedPreferences {
        return com.securepreferences.SecurePreferences(application, ENCRYPT_KEY, PREFS_FILE)
    }

}