package com.neqabty.superneqabty.splash.data.source


import com.neqabty.superneqabty.splash.data.api.AppConfigApi
import com.neqabty.superneqabty.splash.data.model.AppConfig
import javax.inject.Inject

class AppConfigDS @Inject constructor(private val appConfigApi: AppConfigApi) {
    suspend fun appConfig(): AppConfig {
        return appConfigApi.appConfig()
    }
}