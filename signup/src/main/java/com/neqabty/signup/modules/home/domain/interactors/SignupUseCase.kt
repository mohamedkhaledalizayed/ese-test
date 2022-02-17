package com.neqabty.signup.modules.home.domain.interactors

import com.neqabty.signup.modules.home.domain.entity.SignupParams
import com.neqabty.signup.modules.home.domain.entity.UserEntity
import com.neqabty.signup.modules.home.domain.repository.SignupRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SignupUseCase @Inject constructor(private val signupRepository: SignupRepository) {
    fun build(signupParams: SignupParams): Flow<UserEntity> {
        return signupRepository.signup(signupParams)
    }
}