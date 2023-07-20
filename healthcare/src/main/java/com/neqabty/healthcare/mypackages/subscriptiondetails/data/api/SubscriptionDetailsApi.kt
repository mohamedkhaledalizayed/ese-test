package com.neqabty.healthcare.mypackages.subscriptiondetails.data.api

import com.neqabty.healthcare.mypackages.subscriptiondetails.data.model.DeleteFollowerBody
import com.neqabty.healthcare.mypackages.subscriptiondetails.data.model.DeleteFollowerModel
import retrofit2.http.*

interface SubscriptionDetailsApi {

    @POST("vendor/subscribtions/follower-delete")
    suspend fun deleteFollower(@Header("Authorization") token: String, @Body deleteFollowerBody: DeleteFollowerBody): DeleteFollowerModel

}