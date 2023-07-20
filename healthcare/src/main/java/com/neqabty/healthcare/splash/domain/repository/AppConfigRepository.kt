package com.neqabty.healthcare.splash.domain.repository

import com.neqabty.healthcare.splash.data.model.AppConfig
import kotlinx.coroutines.flow.Flow

interface AppConfigRepository {
    fun appConfig(): Flow<AppConfig>
}