package com.neqabty.meganeqabty.splash.domain.repository

import com.neqabty.meganeqabty.splash.data.model.AppConfig
import kotlinx.coroutines.flow.Flow

interface AppConfigRepository {
    fun appConfig(): Flow<AppConfig>
}