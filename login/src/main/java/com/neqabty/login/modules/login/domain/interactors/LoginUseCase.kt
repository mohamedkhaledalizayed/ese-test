package com.neqabty.login.modules.login.domain.interactors

import com.neqabty.login.modules.login.domain.entity.UserEntity
import com.neqabty.login.modules.login.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val authRepository: AuthRepository) {
    fun build(mobile: String, password: String): Flow<UserEntity> {
        return authRepository.login(mobile,password)
    }
}