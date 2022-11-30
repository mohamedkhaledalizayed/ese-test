package com.neqabty.healthcare.auth.logout.domain.interactors


import com.neqabty.healthcare.auth.logout.domain.repository.LogoutRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LogoutUseCase @Inject constructor(private val logoutRepository: LogoutRepository) {

    fun build(): Flow<Boolean> {
        return logoutRepository.logout()
    }

}