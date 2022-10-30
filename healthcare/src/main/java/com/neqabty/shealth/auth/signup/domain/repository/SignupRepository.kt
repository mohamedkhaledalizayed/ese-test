package com.neqabty.shealth.auth.signup.domain.repository



import com.neqabty.shealth.auth.signup.data.model.NeqabtySignupBody
import com.neqabty.shealth.auth.signup.domain.entity.UserEntity
import kotlinx.coroutines.flow.Flow

interface SignupRepository {
    fun signUpNeqabtyMember(neqabtySignupBody: NeqabtySignupBody): Flow<UserEntity>
}