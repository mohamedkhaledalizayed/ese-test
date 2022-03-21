package com.neqabty.superneqabty.splash.data.api

import com.neqabty.superneqabty.splash.data.model.AppConfig
import retrofit2.http.GET


interface AppConfigApi {
    @GET("api/api_configurations")
    suspend fun appConfig(): AppConfig
}