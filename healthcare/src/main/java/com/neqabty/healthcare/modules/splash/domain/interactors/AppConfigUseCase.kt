package com.neqabty.healthcare.modules.splash.domain.interactors

import com.neqabty.healthcare.modules.splash.data.model.AppConfig
import com.neqabty.healthcare.modules.splash.domain.repository.AppConfigRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AppConfigUseCase @Inject constructor(private val appConfigRepository: AppConfigRepository) {
    fun build(): Flow<AppConfig> {
        return appConfigRepository.appConfig()
    }
}