package com.neqabty.signup.modules.signup.domain.repository

import com.neqabty.signup.modules.signup.data.model.NeqabtySignupBody
import com.neqabty.signup.modules.signup.data.model.syndicatemember.UserModel
import com.neqabty.signup.modules.signup.domain.entity.SignupParams
import com.neqabty.signup.modules.signup.domain.entity.UserEntity
import com.neqabty.signup.modules.signup.domain.entity.syndicate.SyndicateListEntity
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface SignupRepository {
    fun signup(signupParams: SignupParams): Flow<Response<UserModel>>
    fun signUpNeqabtyMember(neqabtySignupBody: NeqabtySignupBody): Flow<UserEntity>
    fun  getSyndicates():  Flow<List<SyndicateListEntity>>
}