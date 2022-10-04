package com.neqabty.signup.modules.servicessyndicate.data.source


import com.neqabty.signup.modules.servicessyndicate.data.api.SignupApi
import com.neqabty.signup.modules.servicessyndicate.data.model.SignUpBody
import com.neqabty.signup.modules.servicessyndicate.data.model.signup.SignUpModel
import retrofit2.Response
import javax.inject.Inject

class SignupDS @Inject constructor(private val signupApi: SignupApi) {

    suspend fun signup(signupBody: SignUpBody): Response<SignUpModel> {
        return signupApi.signup(signupBody)
    }

}