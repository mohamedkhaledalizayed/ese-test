package com.neqabty.healthcare.modules.splash.domain.repository

import com.neqabty.healthcare.modules.splash.data.model.AppConfig
import kotlinx.coroutines.flow.Flow

interface AppConfigRepository {
    fun appConfig(): Flow<AppConfig>
}