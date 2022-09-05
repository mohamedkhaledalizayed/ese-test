package com.neqabty.healthcare.modules.splash.data.api

import com.neqabty.healthcare.modules.splash.data.model.AppConfig
import retrofit2.http.GET


interface AppConfigApi {
    @GET("api_configurations")
    suspend fun appConfig(): AppConfig
}