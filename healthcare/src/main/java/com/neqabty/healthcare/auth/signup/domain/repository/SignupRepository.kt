package com.neqabty.healthcare.auth.signup.domain.repository



import com.neqabty.healthcare.auth.signup.data.model.NeqabtySignupBody
import com.neqabty.healthcare.auth.signup.domain.entity.UserEntity
import kotlinx.coroutines.flow.Flow

interface SignupRepository {
    fun signUpNeqabtyMember(neqabtySignupBody: NeqabtySignupBody): Flow<UserEntity>
}