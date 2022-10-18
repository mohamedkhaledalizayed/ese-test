package com.neqabty.signup.modules.signup.domain.interactors

import com.neqabty.signup.modules.signup.data.model.NeqabtySignupBody
import com.neqabty.signup.modules.signup.data.model.syndicatemember.UserModel
import com.neqabty.signup.modules.signup.domain.entity.SignupParams
import com.neqabty.signup.modules.signup.domain.entity.UserEntity
import com.neqabty.signup.modules.signup.domain.entity.syndicate.SyndicateListEntity
import com.neqabty.signup.modules.signup.domain.repository.SignupRepository
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject

class SignupUseCase @Inject constructor(private val signupRepository: SignupRepository) {

    fun build(signupParams: Any): Flow<Response<UserModel>> {
        return signupRepository.signup(signupParams)
    }

    fun build(neqabtySignupBody: NeqabtySignupBody): Flow<UserEntity> {
        return signupRepository.signUpNeqabtyMember(neqabtySignupBody)
    }

    fun build(): Flow<List<SyndicateListEntity>> {
        return signupRepository.getSyndicates()
    }

}