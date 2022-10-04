package com.neqabty.signup.modules.servicessyndicate.domain.interactors

import com.neqabty.signup.modules.servicessyndicate.data.model.signup.SignUpModel
import com.neqabty.signup.modules.servicessyndicate.domain.entity.SignupParams
import com.neqabty.signup.modules.servicessyndicate.domain.repository.SignupRepository
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject

class SignupUseCase @Inject constructor(private val signupRepository: SignupRepository) {

    fun build(signupParams: SignupParams): Flow<Response<SignUpModel>> {
        return signupRepository.signup(signupParams)
    }
}