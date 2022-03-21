package com.neqabty.superneqabty.splash.domain.repository

import com.neqabty.superneqabty.splash.data.model.AppConfig
import kotlinx.coroutines.flow.Flow

interface AppConfigRepository {
    fun appConfig(): Flow<AppConfig>
}