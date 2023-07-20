package com.neqabty.healthcare.splash.data.repository

import com.neqabty.healthcare.splash.data.model.AppConfig
import com.neqabty.healthcare.splash.data.source.AppConfigDS
import com.neqabty.healthcare.splash.domain.repository.AppConfigRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AppConfigRepositoryImpl @Inject constructor(private val appConfigDS: AppConfigDS) :
    AppConfigRepository {
    override fun appConfig(): Flow<AppConfig> {
        return flow {
            emit(appConfigDS.appConfig())
        }
    }
}

