package com.neqabty.healthcare.sustainablehealth.subscribtions.data.source


import com.neqabty.healthcare.core.data.PreferencesHelper
import com.neqabty.healthcare.sustainablehealth.subscribtions.data.api.SubscriptionApi
import com.neqabty.healthcare.sustainablehealth.subscribtions.data.model.SubscribePostBodyRequest
import com.neqabty.healthcare.sustainablehealth.subscribtions.data.model.relationstypes.Relation
import com.neqabty.healthcare.sustainablehealth.subscribtions.data.model.subscription.SubscriptionModel
import javax.inject.Inject

class SubscriptionSource @Inject constructor(private val subscriptionApi: SubscriptionApi, private val sharedPreferences: PreferencesHelper) {

    suspend fun getRelations(): List<Relation>{
        return subscriptionApi.getRelations().data.relations
    }
    suspend fun addSubscription(subscribePostBodyRequest: SubscribePostBodyRequest): SubscriptionModel {

        return subscriptionApi.addSubscription(
            token = "Bearer ${sharedPreferences.token}",
            subscribePostBodyRequest = subscribePostBodyRequest
        )
    }
}