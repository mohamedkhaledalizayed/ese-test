package com.neqabty.healthcare.splash.data.api

import com.neqabty.healthcare.splash.data.model.AppConfig
import retrofit2.http.GET


interface AppConfigApi {
    @GET("api/api_configurations")
    suspend fun appConfig(): AppConfig
}