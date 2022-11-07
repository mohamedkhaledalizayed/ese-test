package com.neqabty.chefaa.core.di

import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import okhttp3.Interceptor
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
    @Named("chefaa")
    fun providesBaseUrl(): String {
        return "https://seha.neqabty.com/public/api/v1/chefaa/"
    }

    @Provides
    @Named("chefaa")
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return interceptor
    }

    @Provides
    @Named("chefaa")
    fun provideOkHttpClient(
        @Named("chefaa") loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        val okHttpClient = OkHttpClient().newBuilder()
        okHttpClient.callTimeout(200, TimeUnit.SECONDS)
        okHttpClient.connectTimeout(200, TimeUnit.SECONDS)
        okHttpClient.readTimeout(200, TimeUnit.SECONDS)
        okHttpClient.writeTimeout(200, TimeUnit.SECONDS)
            .addInterceptor(Interceptor { chain ->

                val request = chain.request()
                var newRequest = request.newBuilder()
//                    .header("Authorization", "Bearer " + Constants.jwt)
                    .header("Accept", "application/json")
                    .build()

                chain.proceed(newRequest)

            })
        okHttpClient.addInterceptor(loggingInterceptor)
        okHttpClient.build()
        return okHttpClient.build()
    }

    @Provides
    @Named("chefaa")
    fun provideConverterFactory(): Converter.Factory {
        return GsonConverterFactory.create(GsonBuilder().serializeNulls().create())
    }

    @Provides
    @Named("chefaa")
    fun provideRetrofitClient(
        @Named("chefaa") okHttpClient: OkHttpClient,
        @Named("chefaa") baseUrl: String,
        @Named("chefaa") converterFactory: Converter.Factory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(converterFactory)
            .build()
    }
//
//    @Provides
//    @Named("chefaa")
//    fun provideRetrofitClientPrescriptions(
//        @Named("chefaa") okHttpClient: OkHttpClient,
//        baseUrl: String,
//        converterFactory: Converter.Factory
//    ): Retrofit {
//        return Retrofit.Builder()
//            .baseUrl(baseUrl)
//            .client(okHttpClient)
//            .addConverterFactory(converterFactory)
//            .build()
//    }
}