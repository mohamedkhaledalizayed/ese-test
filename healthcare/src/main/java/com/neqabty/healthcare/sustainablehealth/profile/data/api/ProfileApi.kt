package com.neqabty.healthcare.sustainablehealth.profile.data.api


import com.neqabty.healthcare.sustainablehealth.profile.data.model.UpdatePasswordBody
import com.neqabty.healthcare.sustainablehealth.profile.data.model.profile.ProfileModel
import com.neqabty.healthcare.sustainablehealth.profile.data.model.updatepaswword.UpdatePasswordModel
import retrofit2.Response
import retrofit2.http.*

interface ProfileApi {

    @GET("accounts/profile")
    suspend fun getUserProfile(@Header("Authorization") token: String, @Query("special-format") platform: String = "android"): ProfileModel

    @POST("accounts/change_password")
    suspend fun updatePassword(@Header("Authorization") token: String, @Body body: UpdatePasswordBody): Response<UpdatePasswordModel>

}