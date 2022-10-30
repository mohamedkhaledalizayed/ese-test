package com.neqabty.shealth.sustainablehealth.splash.data.api

import com.neqabty.shealth.sustainablehealth.splash.data.model.AppConfig
import retrofit2.http.GET


interface AppConfigApi {
    @GET("api_configurations")
    suspend fun appConfig(): AppConfig
}