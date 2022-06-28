package com.neqabty.meganeqabty.splash.domain.interactors

import com.neqabty.meganeqabty.splash.data.model.AppConfig
import com.neqabty.meganeqabty.splash.domain.repository.AppConfigRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AppConfigUseCase @Inject constructor(private val appConfigRepository: AppConfigRepository) {
    fun build(): Flow<AppConfig> {
        return appConfigRepository.appConfig()
    }
}