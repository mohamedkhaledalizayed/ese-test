package com.neqabty.healthcare.splash.domain.interactors

import com.neqabty.healthcare.splash.data.model.AppConfig
import com.neqabty.healthcare.splash.domain.repository.AppConfigRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AppConfigUseCase @Inject constructor(private val appConfigRepository: AppConfigRepository) {
    fun build(): Flow<AppConfig> {
        return appConfigRepository.appConfig()
    }
}