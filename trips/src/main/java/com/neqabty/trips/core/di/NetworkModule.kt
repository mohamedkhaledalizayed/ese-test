package com.neqabty.trips.core.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    @Provides
    @Named("trips")
    fun providesBaseUrl(): String {
        return "https://trips.ese.et3.co/api/"
    }

    @Provides
    @Named("trips")
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return interceptor
    }

    @Provides
    @Named("trips")
    fun provideOkHttpClient(
        @Named("trips")
        loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        val okHttpClient = OkHttpClient().newBuilder()
        okHttpClient.callTimeout(40, TimeUnit.SECONDS)
        okHttpClient.connectTimeout(40, TimeUnit.SECONDS)
        okHttpClient.readTimeout(40, TimeUnit.SECONDS)
        okHttpClient.writeTimeout(40, TimeUnit.SECONDS)
        okHttpClient.addInterceptor(loggingInterceptor)
        okHttpClient.addInterceptor(Interceptor { chain ->
            val request = chain.request()
            var newRequest = request.newBuilder()
                .header("Accept", "application/json")
                .removeHeader("Content-Type")
                .build()
            var newResponse = chain.proceed(newRequest)
            newResponse
        })
        okHttpClient.build()
        return okHttpClient.build()
    }

    @Provides
    @Named("trips")
    fun provideConverterFactory(): Converter.Factory {
        val gson = GsonBuilder()
            .setLenient()
            .create()
        return GsonConverterFactory.create(gson)
    }

    @Provides
    @Named("trips")
    fun provideRetrofitClient(
        @Named("trips")
        okHttpClient: OkHttpClient,
        @Named("trips")
        baseUrl: String,
        @Named("trips")
        converterFactory: Converter.Factory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(converterFactory)
            .build()
    }
}