package com.neqabty.signup.modules.servicessyndicate.data.repository


import com.neqabty.signup.modules.servicessyndicate.data.model.SignUpBody
import com.neqabty.signup.modules.servicessyndicate.data.model.signup.SignUpModel
import com.neqabty.signup.modules.servicessyndicate.data.source.SignupDS
import com.neqabty.signup.modules.servicessyndicate.domain.entity.SignupParams
import com.neqabty.signup.modules.servicessyndicate.domain.repository.SignupRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject

class SignupRepositoryImpl @Inject constructor(private val signupDS: SignupDS) : SignupRepository {
    override fun signup(signupParams: SignupParams): Flow<Response<SignUpModel>> {
        return flow { emit(signupDS.signup(signupParams.toSignupBody())) }
    }
}





private fun SignupParams.toSignupBody(): SignUpBody {
    return SignUpBody(
        entityCode = entityCode,
        email = email,
        mobile = mobile,
        nationalId = national_id)
}
