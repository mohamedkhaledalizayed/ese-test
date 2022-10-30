package com.neqabty.healthcare.auth.signup.data.api


import com.neqabty.healthcare.auth.signup.data.model.NeqabtySignupBody
import com.neqabty.healthcare.auth.signup.data.model.neqabtymember.NeqabtyMemberModel
import retrofit2.http.*

interface SignupApi {

    @POST("accounts/general_signup")
    suspend fun signUpNeqabtyMember(@Body neqabtySignupBody: NeqabtySignupBody): NeqabtyMemberModel

}