package com.neqabty.healthcare.mypackages.addfollower.data.api

import com.neqabty.healthcare.mypackages.addfollower.data.model.AddFollowerBody
import com.neqabty.healthcare.mypackages.addfollower.data.model.addfollower.AddFollowerModel
import retrofit2.http.*

interface AddFollowerApi {

    @POST("healthcare/api/v1/vendor/subscribtions/follower-request")
    suspend fun addFollower(@Header("Authorization") token: String, @Body addFollowerBody: AddFollowerBody): AddFollowerModel

}