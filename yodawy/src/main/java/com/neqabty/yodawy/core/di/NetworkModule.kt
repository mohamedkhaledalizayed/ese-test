package com.neqabty.yodawy.core.di

import com.neqabty.yodawy.BuildConfig
import com.neqabty.yodawy.core.data.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import okhttp3.CertificatePinner
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
@InstallIn(ViewModelComponent::class)
class NetworkModule {
    @Provides
    fun providesBaseUrl(): String {
        return if(BuildConfig.DEBUG) Constants.DEV_SERVER else Constants.PRO_SERVER
    }

    @Provides
    @Named("yodawy")
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = if(BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else  HttpLoggingInterceptor.Level.NONE
        return interceptor
    }

    @Provides
    @Named("yodawy")
    fun provideOkHttpClient(
        @Named("yodawy") loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        val certificatePinner : CertificatePinner = CertificatePinner.Builder()
            .add(
                "front.neqabty.com",
                "sha256/hgx3/z5ENRCVF9jDSHk8GwocaQsEFYAW8ON1eGs2qUc="
            )
            .add(
                "*.neqabty.com",
                "sha256/nt7kxSg6amgrDYO0JQOM+d3Q+G0fgFtBdx76ppVzIS4="
            ).build()

        val okHttpClient = OkHttpClient().newBuilder()
        okHttpClient.callTimeout(40, TimeUnit.SECONDS)
        okHttpClient.connectTimeout(40, TimeUnit.SECONDS)
        okHttpClient.readTimeout(40, TimeUnit.SECONDS)
        okHttpClient.writeTimeout(40, TimeUnit.SECONDS)
        okHttpClient.addInterceptor(object : Interceptor {
            override fun intercept(chain: Interceptor.Chain): Response {
                val request = chain.request()
                if (request.header("X-Yodawy-Signature") != null) {
                    var newRequest = request.newBuilder()
                        .header("Authorization", "Basic " + Constants.FIXED_TOKEN)
                        .build()
                    var newResponse = chain.proceed(newRequest)
                    return newResponse
                } else {
                    var newRequest = request.newBuilder()
                        .header("Authorization", "Bearer " + Constants.jwt)
                        .build()
                    var newResponse = chain.proceed(newRequest)
                    return newResponse
                }
            }
        })
        okHttpClient.addInterceptor(loggingInterceptor)
        okHttpClient.certificatePinner(certificatePinner)
        okHttpClient.build()
        return okHttpClient.build()
    }

    @Provides
    fun provideConverterFactory(): Converter.Factory {
        return GsonConverterFactory.create()
    }

    @Provides
    @Named("yodawy")
    fun provideRetrofitClient(
        @Named("yodawy") okHttpClient: OkHttpClient,
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