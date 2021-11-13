package com.neqabty.yodawy.core

import android.app.Application
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.neqabty.yodawy.modules.products.presentation.view.productscreen.SearchActivity
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

class RetrofitModule private constructor(private val context: Application) {
    private lateinit var cache: Cache
    private lateinit var gson: Gson
    private lateinit var okHttpClient: OkHttpClient
    private lateinit var retrofit: Retrofit
    private lateinit var service: SearchActivity.ServiceApi

    private fun provideCache() {
        val cacheSize = (10 * 1024 * 1024).toLong()
        try {
            val myDir = File(context.cacheDir, "response")
            myDir.mkdir()
            cache = Cache(myDir, cacheSize)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun provideGson() {
        val gsonBuilder = GsonBuilder()
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
        gson = gsonBuilder.create()
    }

    private fun provideOkhttpClient() {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        okHttpClient = OkHttpClient.Builder()
            .writeTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .addInterceptor(Interceptor { chain ->
                val request = chain.request()

                var newRequest = request.newBuilder()

                    .header("Authorization", "Bearer " + "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImp0aSI6ImFkM2FkOGM0OTc1YTgyNzE3ZDkyMzIxMjkyOTc0YzdjOGVmZDE5NjlmZjJlZDBmYjdjN2JlNjk1ZDY1MzU2NTljMjhlYTE5YTY1MzI5YzdjIn0.eyJhdWQiOiIxIiwianRpIjoiYWQzYWQ4YzQ5NzVhODI3MTdkOTIzMjEyOTI5NzRjN2M4ZWZkMTk2OWZmMmVkMGZiN2M3YmU2OTVkNjUzNTY1OWMyOGVhMTlhNjUzMjljN2MiLCJpYXQiOjE2MzY2Njk3ODUsIm5iZiI6MTYzNjY2OTc4NSwiZXhwIjoxNjY4MjA1Nzg1LCJzdWIiOiIxNDA3NDMiLCJzY29wZXMiOltdfQ.Nl4_lETjjVqgfaC_Dy8_BryK1majRgame9rCF79M_dwh6A3yTpkVIzcIN77WzQSiBMFSD4gQ0VuLwgHbOwosswMEm_VNq51u-QPpFkEmzO6rNLr8g_3cVfvyua_P5E7ltFQCdvkuEFly7PiFa4uIoHAxr3vcGPxeCzMJIxFGRcuREW43D-KCYpTlqENNfB3qpRXC8jdoxVDnZE4ACSmWgI2k_NS4-2RDQWUPiy37ocm7ep_JNYdVKo-YSgLaRwb810I91gz8CIQKIzfs12n1w0hSdS-xMDShA9XPLRAkUxfrrTiQtXLEc8aqGjm0IdK5jByJxJ-qxOMC2nTD6dYlMkcvgDwCxIVoz7s0vphXOGq1t1Uz6PyMlnPkqwjp4EPlcCUl2XaIAXiNVJN7l7HBOD9E5t5gwT-dQx_ZHGoibSEFnYCFD-w6wOKL6bKrMjmSHD_5ejFMBRVrlcFTNTwto1Wogox1gMyimqWoWmF22KX5HASi1VU6DMlBrLFaBK6-Uo4-l72KOnspAsKziVmUmJOcoJSKR5FgvYLZL1XQTyHXpCcbpRx6GrDF_t6qkvYDVsSnrcoJKEJdCsg26VVJtmdNNHam3rrovVKquS5XZx4C8wVAN5ElWoTCKfzVVXc_nZ8VX7YmaM5cmdpUx_cVwBQfc8yp0TCh7Mxc1mZR58s")
                    .header("Accept", "application/json")
                    .build()

                var newResponse = chain.proceed(newRequest)

                newResponse
            })
            .addInterceptor(interceptor)
            .build()
    }

    private fun provideRetrofit() {
        retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl("http://3.131.229.146:44392/api/v1/yodawy/")
            .client(okHttpClient)
            .build()
    }

    private fun provideService() {
        service = retrofit.create(SearchActivity.ServiceApi::class.java)
    }

    companion object {
        var instance: RetrofitModule? = null

        fun initialize(context: Application) {
            instance = RetrofitModule(context)
        }
    }

    fun getService(): SearchActivity.ServiceApi {
        return service
    }

    init {
        provideCache()
        provideGson()
        provideOkhttpClient()
        provideRetrofit()
        provideService()
    }


}