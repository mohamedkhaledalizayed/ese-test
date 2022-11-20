package com.neqabty.healthcare.core.di


import com.neqabty.healthcare.core.data.Constants
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
class MegaModule {
    @Provides
    @Named("mega")
    fun providesBaseUrl(): String {
        return Constants.BASE_URL_Main
    }

    @Provides
    @Named("mega")
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
    @Named("mega")
    fun provideOkHttpClient(
        @Named("mega")
        loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {

        val certificatePinner : CertificatePinner = CertificatePinner.Builder()
            .add(
                "neqabty.com",
                "sha256/nt7kxSg6amgrDYO0JQOM+d3Q+G0fgFtBdx76ppVzIS4="
            ).build()
        val okHttpClient = OkHttpClient().newBuilder()
        okHttpClient.callTimeout(40, TimeUnit.SECONDS)
        okHttpClient.connectTimeout(40, TimeUnit.SECONDS)
        okHttpClient.readTimeout(40, TimeUnit.SECONDS)
        okHttpClient.writeTimeout(40, TimeUnit.SECONDS)
        okHttpClient.certificatePinner(certificatePinner)
        okHttpClient.addInterceptor(loggingInterceptor)
        okHttpClient.build()
        okHttpClient.addInterceptor(interceptor)
        return okHttpClient.build()
    }

    @Provides
    @Named("mega")
    fun provideConverterFactory(): Converter.Factory {
        return GsonConverterFactory.create()
    }

    @Provides
    @Named("mega")
    fun provideRetrofitClient(
        @Named("mega")
        okHttpClient: OkHttpClient,
        @Named("mega")
        baseUrl: String,
        @Named("mega")
        converterFactory: Converter.Factory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(converterFactory)
            .build()
    }
}