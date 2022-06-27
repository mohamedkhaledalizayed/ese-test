package com.neqabty.healthcare.modules.subscribtions.data.api

import com.neqabty.healthcare.modules.subscribtions.data.model.SubscribePostBodyRequest
import com.neqabty.healthcare.modules.subscribtions.data.model.subscription.SubscriptionModel
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface SubscriptionApi {
    @POST("vendor/subscribtions/request")
    suspend fun addSubscription(
        @Header("Authorization") token: String,
        @Body subscribePostBodyRequest: SubscribePostBodyRequest): SubscriptionModel
}
