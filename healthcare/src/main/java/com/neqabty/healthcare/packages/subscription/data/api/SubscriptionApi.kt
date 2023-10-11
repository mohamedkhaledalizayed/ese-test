package com.neqabty.healthcare.packages.subscription.data.api

import com.neqabty.healthcare.packages.subscription.data.model.SubscribePostBodyRequest
import com.neqabty.healthcare.packages.subscription.data.model.SubscriptionModel
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface SubscriptionApi {

    @POST("healthcare/api/v1/vendor/subscribtions/request")
    suspend fun addSubscription(
        @Header("Authorization") token: String,
        @Body subscribePostBodyRequest: SubscribePostBodyRequest
    ): SubscriptionModel

}
