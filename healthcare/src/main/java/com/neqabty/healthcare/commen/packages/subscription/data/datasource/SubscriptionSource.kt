package com.neqabty.healthcare.commen.packages.subscription.data.datasource


import com.neqabty.healthcare.commen.packages.subscription.data.api.SubscriptionApi
import com.neqabty.healthcare.commen.packages.subscription.data.model.SubscribePostBodyRequest
import com.neqabty.healthcare.commen.packages.subscription.data.model.SubscriptionModel
import com.neqabty.healthcare.core.data.PreferencesHelper
import javax.inject.Inject

class SubscriptionSource @Inject constructor(
    private val subscriptionApi: SubscriptionApi,
    private val sharedPreferences: PreferencesHelper
) {

    suspend fun addSubscription(subscribePostBodyRequest: SubscribePostBodyRequest): SubscriptionModel {

        return subscriptionApi.addSubscription(
            token = "Token ${sharedPreferences.token}",
            subscribePostBodyRequest = subscribePostBodyRequest
        )
    }

}