package com.neqabty.healthcare.auth.login.domain.interactors


import com.neqabty.healthcare.auth.login.domain.entity.UserEntity
import com.neqabty.healthcare.auth.login.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val authRepository: AuthRepository) {
    fun build(mobile: String, password: String, token: String, firebaseToken: String): Flow<UserEntity> {
        return authRepository.login(mobile,password,token, firebaseToken)
    }
}