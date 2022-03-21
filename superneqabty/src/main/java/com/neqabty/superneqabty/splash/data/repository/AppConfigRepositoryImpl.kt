package com.neqabty.superneqabty.splash.data.repository

import com.neqabty.superneqabty.splash.data.model.AppConfig
import com.neqabty.superneqabty.splash.data.source.AppConfigDS
import com.neqabty.superneqabty.splash.domain.repository.AppConfigRepository
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

