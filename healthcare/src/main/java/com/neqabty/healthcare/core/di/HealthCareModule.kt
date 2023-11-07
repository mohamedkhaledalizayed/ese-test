package com.neqabty.healthcare.core.di

import android.util.Log
import com.google.gson.GsonBuilder
import com.neqabty.healthcare.BuildConfig
import com.neqabty.healthcare.core.data.Constants.BASE_URL_Main
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
class HealthCareModule {
    @Provides
    @Named("healthcare")
    fun providesBaseUrl(): String {
        return BASE_URL_Main
    }

    @Provides
    @Named("healthcare")
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

    @Provides
    @Named("healthcare")
    fun provideOkHttpClient(    @Named("healthcare")
                                loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {

        val okHttpClient = OkHttpClient().newBuilder()
        okHttpClient.callTimeout(60, TimeUnit.SECONDS)
        okHttpClient.connectTimeout(60, TimeUnit.SECONDS)
        okHttpClient.readTimeout(90, TimeUnit.SECONDS)
        okHttpClient.writeTimeout(90, TimeUnit.SECONDS)
//        okHttpClient.certificatePinner(certificatePinner)
        okHttpClient.addInterceptor(loggingInterceptor)

        return okHttpClient.build()
    }

    @Provides
    @Named("healthcare")
    fun provideConverterFactory(): Converter.Factory {
        val gson = GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create()
        return GsonConverterFactory.create(gson)
    }

    @Provides
    @Named("healthcare")
    fun provideRetrofitClient(
        @Named("healthcare")
        okHttpClient: OkHttpClient,
        @Named("healthcare")
        baseUrl: String,
        @Named("healthcare")
        converterFactory: Converter.Factory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(converterFactory)
            .build()
    }
}