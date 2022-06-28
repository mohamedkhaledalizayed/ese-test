package com.neqabty.meganeqabty.splash.data.source


import com.neqabty.meganeqabty.splash.data.api.AppConfigApi
import com.neqabty.meganeqabty.splash.data.model.AppConfig
import javax.inject.Inject

class AppConfigDS @Inject constructor(private val appConfigApi: AppConfigApi) {
    suspend fun appConfig(): AppConfig {
        return appConfigApi.appConfig()
    }
}