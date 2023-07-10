package com.neqabty.healthcare.commen.packages.subscription.data.api

import com.neqabty.healthcare.commen.packages.subscription.data.model.SubscribePostBodyRequest
import com.neqabty.healthcare.commen.packages.subscription.data.model.SubscriptionModel
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface SubscriptionApi {

    @POST("vendor/subscribtions/request")
    suspend fun addSubscription(
        @Header("Authorization") token: String,
        @Body subscribePostBodyRequest: SubscribePostBodyRequest
    ): SubscriptionModel

}
