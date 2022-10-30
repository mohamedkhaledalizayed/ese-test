package com.neqabty.shealth.sustainablehealth.splash.data.source


import com.neqabty.shealth.sustainablehealth.splash.data.api.AppConfigApi
import com.neqabty.shealth.sustainablehealth.splash.data.model.AppConfig
import javax.inject.Inject

class AppConfigDS @Inject constructor(private val appConfigApi: AppConfigApi) {
    suspend fun appConfig(): AppConfig {
        return appConfigApi.appConfig()
    }
}