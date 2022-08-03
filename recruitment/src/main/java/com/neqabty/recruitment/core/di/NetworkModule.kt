package com.neqabty.recruitment.core.di

import com.neqabty.recruitment.BuildConfig
import com.neqabty.recruitment.core.data.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named

@Module
@InstallIn(ViewModelComponent::class)
class NetworkModule {
    @Provides
    fun providesBaseUrl(): String {
        return if(BuildConfig.DEBUG) Constants.DEV_SERVER else Constants.PRO_SERVER
    }

    @Provides
    @Named("recruitment")
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = if(BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else  HttpLoggingInterceptor.Level.NONE
        return interceptor
    }

    @Provides
    @Named("recruitment")
    fun provideOkHttpClient(
        @Named("recruitment") loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {

        val okHttpClient = OkHttpClient().newBuilder()
        okHttpClient.callTimeout(40, TimeUnit.SECONDS)
        okHttpClient.connectTimeout(40, TimeUnit.SECONDS)
        okHttpClient.readTimeout(40, TimeUnit.SECONDS)
        okHttpClient.writeTimeout(40, TimeUnit.SECONDS)
        okHttpClient.addInterceptor(loggingInterceptor)
        okHttpClient.build()
        return okHttpClient.build()
    }

    @Provides
    fun provideConverterFactory(): Converter.Factory {
        return GsonConverterFactory.create()
    }

    @Provides
    @Named("recruitment")
    fun provideRetrofitClient(
        @Named("recruitment") okHttpClient: OkHttpClient,
        baseUrl: String,
        converterFactory: Converter.Factory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(converterFactory)
            .build()
    }
}