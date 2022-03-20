package com.neqabty.signup.modules.home.data.repository

import com.neqabty.signup.modules.home.data.model.SignupBody
import com.neqabty.signup.modules.home.data.model.mappers.toUserEntity
import com.neqabty.signup.modules.home.data.source.SignupDS
import com.neqabty.signup.modules.home.domain.entity.SignupParams
import com.neqabty.signup.modules.home.domain.entity.UserEntity
import com.neqabty.signup.modules.home.domain.repository.SignupRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SignupRepositoryImpl @Inject constructor(private val signupDS: SignupDS) : SignupRepository {
    override fun signup(signupParams: SignupParams): Flow<UserEntity> {
        return flow { emit(signupDS.signup(signupParams.toSignupBody()).toUserEntity()) }
    }
}

private fun SignupParams.toSignupBody(): SignupBody {
    return SignupBody(entityCode, membershipId, mobile, last4_national_id)
}
