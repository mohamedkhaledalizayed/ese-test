package com.neqabty.healthcare.commen.mypackages.addfollower.data.api

import com.neqabty.healthcare.commen.mypackages.addfollower.data.model.AddFollowerBody
import com.neqabty.healthcare.commen.mypackages.addfollower.data.model.addfollower.AddFollowerModel
import retrofit2.http.*

interface AddFollowerApi {

    @POST("vendor/subscribtions/follower-request")
    suspend fun addFollower(@Header("Authorization") token: String, @Body addFollowerBody: AddFollowerBody): AddFollowerModel

}