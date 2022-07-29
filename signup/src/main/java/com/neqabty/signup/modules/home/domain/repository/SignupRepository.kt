package com.neqabty.signup.modules.home.domain.repository

import com.neqabty.signup.modules.home.data.model.NeqabtySignupBody
import com.neqabty.signup.modules.home.domain.entity.SignupParams
import com.neqabty.signup.modules.home.domain.entity.UserEntity
import com.neqabty.signup.modules.home.domain.entity.syndicate.SyndicateListEntity
import kotlinx.coroutines.flow.Flow

interface SignupRepository {
    fun signup(signupParams: SignupParams): Flow<UserEntity>
    fun signUpNeqabtyMember(neqabtySignupBody: NeqabtySignupBody): Flow<UserEntity>
    fun  getSyndicates():  Flow<List<SyndicateListEntity>>
}