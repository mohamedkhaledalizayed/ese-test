package com.neqabty.healthcare.sustainablehealth.splash.domain.repository

import com.neqabty.healthcare.sustainablehealth.splash.data.model.AppConfig
import kotlinx.coroutines.flow.Flow

interface AppConfigRepository {
    fun appConfig(): Flow<AppConfig>
}