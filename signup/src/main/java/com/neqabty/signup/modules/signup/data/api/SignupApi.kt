package com.neqabty.signup.modules.signup.data.api

import com.neqabty.signup.modules.signup.data.model.NeqabtySignupBody
import com.neqabty.signup.modules.signup.data.model.SignupBody
import com.neqabty.signup.modules.signup.data.model.neqabtymember.NeqabtyMemberModel
import com.neqabty.signup.modules.signup.data.model.syndicatemember.UserModel
import com.neqabty.signup.modules.signup.data.model.syndicates.SyndicateListModel
import retrofit2.http.*

interface SignupApi {

    @POST("accounts/signup")
    suspend fun syndicateMember(@Body signupBody: SignupBody): UserModel

    @POST("accounts/general_signup")
    suspend fun signUpNeqabtyMember(@Body neqabtySignupBody: NeqabtySignupBody): NeqabtyMemberModel

    @GET("entities?special-format=android")
    suspend fun getSyndicates(): SyndicateListModel

}