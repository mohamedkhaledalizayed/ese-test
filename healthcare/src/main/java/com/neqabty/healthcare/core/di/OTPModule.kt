package com.neqabty.healthcare.core.di


import com.neqabty.healthcare.BuildConfig
import com.neqabty.healthcare.core.data.Constants
import com.neqabty.healthcare.core.data.Constants.BASE_URL_DEV_OTP
import com.neqabty.healthcare.core.data.Constants.BASE_URL_PRO_OTP
import com.neqabty.healthcare.core.data.Constants.BASE_URL_STAGING_OTP
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
class OTPModule {
    @Provides
    @Named("otp")
    fun providesBaseUrl(): String {
        return BASE_URL_DEV_OTP
    }

    @Provides
    @Named("otp")
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
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
    @Named("otp")
    fun provideOkHttpClient(
        @Named("otp")
        loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        val okHttpClient = OkHttpClient().newBuilder()
        okHttpClient.callTimeout(40, TimeUnit.SECONDS)
        okHttpClient.connectTimeout(40, TimeUnit.SECONDS)
        okHttpClient.readTimeout(40, TimeUnit.SECONDS)
        okHttpClient.writeTimeout(40, TimeUnit.SECONDS)
        okHttpClient.addInterceptor(loggingInterceptor)
        okHttpClient.addInterceptor(interceptor)
        if (!BuildConfig.DEBUG) {
            val certificatePinner : CertificatePinner = CertificatePinner.Builder()
                .add(
                    "community.neqabty.com",
                    "sha256/8Rw90Ej3Ttt8RRkrg+WYDS9n7IS03bk5bjP/UXPtaY8="
                ).build()

            okHttpClient.certificatePinner(certificatePinner)
        }

        return okHttpClient.build()
    }

    @Provides
    @Named("otp")
    fun provideConverterFactory(): Converter.Factory {
        return GsonConverterFactory.create()
    }

    @Provides
    @Named("otp")
    fun provideRetrofitClient(
        @Named("otp")
        okHttpClient: OkHttpClient,
        @Named("otp")
        baseUrl: String,
        @Named("otp")
        converterFactory: Converter.Factory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(converterFactory)
            .build()
    }
}