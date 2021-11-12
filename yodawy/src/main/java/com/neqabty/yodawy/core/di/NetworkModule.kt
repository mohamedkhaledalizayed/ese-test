package com.neqabty.yodawy.core.di

import android.app.ActivityManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    @Provides
    fun providesBaseUrl(): String {
        return "http://3.131.229.146:44392/api/v1/yodawy/"
    }

    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return interceptor
    }

    @Provides
    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        val okHttpClient = OkHttpClient().newBuilder()
        okHttpClient.callTimeout(40, TimeUnit.SECONDS)
        okHttpClient.connectTimeout(40, TimeUnit.SECONDS)
        okHttpClient.readTimeout(40, TimeUnit.SECONDS)
        okHttpClient.writeTimeout(40, TimeUnit.SECONDS)
            .addInterceptor(Interceptor { chain ->
                val request = chain.request()

                var newRequest = request.newBuilder()

                    .header("Authorization", "Bearer " + "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImp0aSI6ImFkM2FkOGM0OTc1YTgyNzE3ZDkyMzIxMjkyOTc0YzdjOGVmZDE5NjlmZjJlZDBmYjdjN2JlNjk1ZDY1MzU2NTljMjhlYTE5YTY1MzI5YzdjIn0.eyJhdWQiOiIxIiwianRpIjoiYWQzYWQ4YzQ5NzVhODI3MTdkOTIzMjEyOTI5NzRjN2M4ZWZkMTk2OWZmMmVkMGZiN2M3YmU2OTVkNjUzNTY1OWMyOGVhMTlhNjUzMjljN2MiLCJpYXQiOjE2MzY2Njk3ODUsIm5iZiI6MTYzNjY2OTc4NSwiZXhwIjoxNjY4MjA1Nzg1LCJzdWIiOiIxNDA3NDMiLCJzY29wZXMiOltdfQ.Nl4_lETjjVqgfaC_Dy8_BryK1majRgame9rCF79M_dwh6A3yTpkVIzcIN77WzQSiBMFSD4gQ0VuLwgHbOwosswMEm_VNq51u-QPpFkEmzO6rNLr8g_3cVfvyua_P5E7ltFQCdvkuEFly7PiFa4uIoHAxr3vcGPxeCzMJIxFGRcuREW43D-KCYpTlqENNfB3qpRXC8jdoxVDnZE4ACSmWgI2k_NS4-2RDQWUPiy37ocm7ep_JNYdVKo-YSgLaRwb810I91gz8CIQKIzfs12n1w0hSdS-xMDShA9XPLRAkUxfrrTiQtXLEc8aqGjm0IdK5jByJxJ-qxOMC2nTD6dYlMkcvgDwCxIVoz7s0vphXOGq1t1Uz6PyMlnPkqwjp4EPlcCUl2XaIAXiNVJN7l7HBOD9E5t5gwT-dQx_ZHGoibSEFnYCFD-w6wOKL6bKrMjmSHD_5ejFMBRVrlcFTNTwto1Wogox1gMyimqWoWmF22KX5HASi1VU6DMlBrLFaBK6-Uo4-l72KOnspAsKziVmUmJOcoJSKR5FgvYLZL1XQTyHXpCcbpRx6GrDF_t6qkvYDVsSnrcoJKEJdCsg26VVJtmdNNHam3rrovVKquS5XZx4C8wVAN5ElWoTCKfzVVXc_nZ8VX7YmaM5cmdpUx_cVwBQfc8yp0TCh7Mxc1mZR58s")
                    .header("Accept", "application/json")
                    .build()

                var newResponse = chain.proceed(newRequest)

                newResponse
            })
        okHttpClient.addInterceptor(loggingInterceptor)
        okHttpClient.build()
        return okHttpClient.build()
    }

    @Provides
    fun provideConverterFactory(): Converter.Factory {
        return GsonConverterFactory.create()
    }

    @Provides
    fun provideRetrofitClient(
        okHttpClient: OkHttpClient,
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