package com.neqabty.signup.modules.home.data.api

import com.neqabty.signup.modules.home.data.model.SignupBody
import com.neqabty.signup.modules.home.data.model.UserModel
import retrofit2.http.Body
import retrofit2.http.POST

interface SignupApi {
    @POST("accounts/signup")
    suspend fun signup(@Body signupBody: SignupBody): UserModel
}