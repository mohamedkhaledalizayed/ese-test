package com.neqabty.meganeqabty.core.di


import com.neqabty.core.data.Constants.BASE_URL_STAGING_SYNDICATE
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
class NetworkModule {
    @Provides
    @Named("syndicate")
    fun providesBaseUrl(): String {
        return BASE_URL_STAGING_SYNDICATE
    }

    @Provides
    @Named("syndicate")
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return interceptor
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
    @Named("syndicate")
    fun provideOkHttpClient(
        @Named("syndicate")
        loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        val okHttpClient = OkHttpClient().newBuilder()
        okHttpClient.callTimeout(40, TimeUnit.SECONDS)
        okHttpClient.connectTimeout(40, TimeUnit.SECONDS)
        okHttpClient.readTimeout(40, TimeUnit.SECONDS)
        okHttpClient.writeTimeout(40, TimeUnit.SECONDS)
        okHttpClient.addInterceptor(loggingInterceptor)
        okHttpClient.build()
        okHttpClient.addInterceptor(interceptor)
        return okHttpClient.build()
    }

    @Provides
    @Named("syndicate")
    fun provideConverterFactory(): Converter.Factory {
        return GsonConverterFactory.create()
    }

    @Provides
    @Named("syndicate")
    fun provideRetrofitClient(
        @Named("syndicate")
        okHttpClient: OkHttpClient,
        @Named("syndicate")
        baseUrl: String,
        @Named("syndicate")
        converterFactory: Converter.Factory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(converterFactory)
            .build()
    }
}