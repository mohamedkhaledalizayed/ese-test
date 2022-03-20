package com.neqabty.signup.modules.home.domain.repository

import com.neqabty.signup.modules.home.domain.entity.SignupParams
import com.neqabty.signup.modules.home.domain.entity.UserEntity
import kotlinx.coroutines.flow.Flow

interface SignupRepository {
    fun signup(signupParams: SignupParams): Flow<UserEntity>
}