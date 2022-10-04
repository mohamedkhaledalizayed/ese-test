package com.neqabty.signup.modules.servicessyndicate.data.api


import com.neqabty.signup.modules.servicessyndicate.data.model.SignUpBody
import com.neqabty.signup.modules.servicessyndicate.data.model.signup.SignUpModel
import retrofit2.Response
import retrofit2.http.*

interface SignupApi {

    @POST("accounts/signup")
    suspend fun signup(@Body signupBody: SignUpBody): Response<SignUpModel>

}