package com.neqabty.healthcare.auth.signup.domain.repository



import com.neqabty.healthcare.auth.signup.data.model.NeqabtySignupBody
import com.neqabty.healthcare.auth.signup.data.model.syndicatemember.UserModel
import com.neqabty.healthcare.auth.signup.domain.entity.UserEntity
import com.neqabty.healthcare.auth.signup.domain.entity.syndicate.SyndicateListEntity
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface SignupRepository {
    fun signup(signupParams: Any): Flow<Response<UserModel>>
    fun signupMember(neqabtySignupBody: NeqabtySignupBody): Flow<UserEntity>
    fun  getSyndicates():  Flow<List<SyndicateListEntity>>
}