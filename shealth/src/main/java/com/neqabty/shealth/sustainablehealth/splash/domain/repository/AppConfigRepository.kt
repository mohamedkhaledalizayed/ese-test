package com.neqabty.shealth.sustainablehealth.splash.domain.repository

import com.neqabty.shealth.sustainablehealth.splash.data.model.AppConfig
import kotlinx.coroutines.flow.Flow

interface AppConfigRepository {
    fun appConfig(): Flow<AppConfig>
}