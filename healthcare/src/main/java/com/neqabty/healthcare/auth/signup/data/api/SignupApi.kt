package com.neqabty.healthcare.auth.signup.data.api


import com.neqabty.healthcare.auth.signup.data.model.NeqabtySignupBody
import com.neqabty.healthcare.auth.signup.data.model.UpgradeMemberBody
import com.neqabty.healthcare.auth.signup.data.model.neqabtymember.NeqabtyMemberModel
import com.neqabty.healthcare.auth.signup.data.model.syndicatemember.UserModel
import com.neqabty.healthcare.auth.signup.data.model.syndicates.SyndicateListModel
import com.neqabty.healthcare.core.data.ApiResponse
import retrofit2.Response
import retrofit2.http.*

interface SignupApi {

    @POST("api/accounts/swap")
    suspend fun upgradeMember(@Header("Authorization") token: String, @Body upgradeMemberBody: UpgradeMemberBody): ApiResponse<NeqabtyMemberModel>

    @POST("api/accounts/register")
    suspend fun signupMember(@Body neqabtySignupBody: NeqabtySignupBody): ApiResponse<NeqabtyMemberModel>

    @GET("api/entities?special-format=android")
    suspend fun getSyndicates(@Query("filter{type.name}") type: String = "syndicate"): SyndicateListModel

}