package com.neqabty.healthcare.commen.splash.domain.repository

import com.neqabty.healthcare.commen.splash.data.model.AppConfig
import kotlinx.coroutines.flow.Flow

interface AppConfigRepository {
    fun appConfig(): Flow<AppConfig>
}