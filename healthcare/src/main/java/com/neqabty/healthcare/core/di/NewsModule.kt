package com.neqabty.healthcare.core.di

import com.neqabty.healthcare.BuildConfig
import com.neqabty.healthcare.core.data.Constants
import com.neqabty.healthcare.core.data.Constants.BASE_URL_PRO_NEWS
import com.neqabty.healthcare.core.data.Constants.BASE_URL_STAGING_NEWS
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.CertificatePinner
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
class NewsModule {
    @Provides
    @Named("newsModule")
    fun providesBaseUrl(): String {
        return BASE_URL_PRO_NEWS
    }

    @Provides
    @Named("newsModule")
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        return interceptor
    }

    @Provides
    @Named("newsModule")
    fun provideOkHttpClient(
        @Named("newsModule")loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {

        val okHttpClient = OkHttpClient().newBuilder()
        okHttpClient.callTimeout(40, TimeUnit.SECONDS)
        okHttpClient.connectTimeout(40, TimeUnit.SECONDS)
        okHttpClient.readTimeout(40, TimeUnit.SECONDS)
        okHttpClient.writeTimeout(40, TimeUnit.SECONDS)
        okHttpClient.addInterceptor(loggingInterceptor)
        okHttpClient.addInterceptor(interceptor)
        okHttpClient.build()
        if (!com.neqabty.chefaa.BuildConfig.DEBUG) {
            val certificatePinner : CertificatePinner = CertificatePinner.Builder()
                .add(
                    "news.neqabty.com",
                    "sha256/8Rw90Ej3Ttt8RRkrg+WYDS9n7IS03bk5bjP/UXPtaY8="
                ).build()

            okHttpClient.certificatePinner(certificatePinner)
        }
        return okHttpClient.build()
    }

    @Provides
    @Named("newsModule")
    fun provideConverterFactory(): Converter.Factory {
        return GsonConverterFactory.create()
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
        original  = requestBuilder.build()
        it.proceed(original)

    }
    @Provides
    @Named("newsModule")
    fun provideRetrofitClient(
        @Named("newsModule")
        okHttpClient: OkHttpClient,
        @Named("newsModule")
        baseUrl: String,
        @Named("newsModule")
        converterFactory: Converter.Factory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(converterFactory)
            .build()
    }
}