package com.neqabty.healthcare.modules.profile.data.api

import com.neqabty.healthcare.modules.profile.data.model.profile.ProfileModel
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ProfileApi {

    @FormUrlEncoded
    @POST("client/profile")
    suspend fun getProfile(@Field("mobile") phone: String): ProfileModel

}