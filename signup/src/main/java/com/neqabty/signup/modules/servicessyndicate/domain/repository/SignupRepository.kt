package com.neqabty.signup.modules.servicessyndicate.domain.repository

import com.neqabty.signup.modules.servicessyndicate.data.model.signup.SignUpModel
import com.neqabty.signup.modules.servicessyndicate.domain.entity.SignupParams
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface SignupRepository {
    fun signup(signupParams: SignupParams): Flow<Response<SignUpModel>>
}