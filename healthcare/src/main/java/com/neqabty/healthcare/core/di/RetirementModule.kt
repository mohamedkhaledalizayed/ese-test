package com.neqabty.healthcare.core.di

import android.util.Log
import com.neqabty.healthcare.BuildConfig
import com.neqabty.healthcare.core.data.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
class RetirementModule {
    @Provides
    @Named("retirement")
    fun providesBaseUrl(): String {
        return Constants.BASE_URL_RETIREMENT
    }

    @Provides
    @Named("retirement")
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor(object: HttpLoggingInterceptor.Logger{
            override fun log(message: String) {
//                Timber.tag("OkHttp").d(message)
                if(BuildConfig.DEBUG)
                Log.d("OkHttp" ,message)
                else{}
            }
        })
        logging.level = HttpLoggingInterceptor.Level.BODY
        return logging
    }

    private val interceptor = Interceptor {
        var original = it.request()
        val originalHttpUrl = original.url
        val url = originalHttpUrl
            .newBuilder()
            .build()
        val requestBuilder = original.newBuilder().url(url)
        requestBuilder.addHeader("Content-Type", "application/json")
        requestBuilder.addHeader("Accept","application/json")
        original = requestBuilder.build()

        it.proceed(original)

    }

    @Provides
    @Named("retirement")
    fun provideOkHttpClient(
        @Named("retirement")
        loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        val okHttpClient = OkHttpClient().newBuilder()
        okHttpClient.callTimeout(40, TimeUnit.SECONDS)
        okHttpClient.connectTimeout(40, TimeUnit.SECONDS)
        okHttpClient.readTimeout(40, TimeUnit.SECONDS)
        okHttpClient.writeTimeout(40, TimeUnit.SECONDS)
        okHttpClient.addInterceptor(loggingInterceptor)
        okHttpClient.addInterceptor(interceptor)
        return okHttpClient.build()
    }

    @Provides
    @Named("retirement")
    fun provideConverterFactory(): Converter.Factory {
        return GsonConverterFactory.create()
    }

    @Provides
    @Named("retirement")
    fun provideRetrofitClient(
        @Named("retirement")
        okHttpClient: OkHttpClient,
        @Named("retirement")
        baseUrl: String,
        @Named("retirement")
        converterFactory: Converter.Factory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(converterFactory)
            .build()
    }

}