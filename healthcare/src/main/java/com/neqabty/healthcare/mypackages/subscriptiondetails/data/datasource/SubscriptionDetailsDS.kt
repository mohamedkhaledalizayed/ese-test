package com.neqabty.healthcare.mypackages.subscriptiondetails.data.datasource

import com.neqabty.healthcare.core.data.PreferencesHelper
import com.neqabty.healthcare.mypackages.subscriptiondetails.data.api.SubscriptionDetailsApi
import com.neqabty.healthcare.mypackages.subscriptiondetails.data.model.DeleteFollowerBody
import javax.inject.Inject

class SubscriptionDetailsDS @Inject constructor(private val subscriptionDetailsApi: SubscriptionDetailsApi,
                                                private val sharedPreferences: PreferencesHelper) {

    suspend fun deleteFollower(deleteFollowerBody: DeleteFollowerBody): Boolean {
        return subscriptionDetailsApi.deleteFollower(token = "Token ${sharedPreferences.token}", deleteFollowerBody).status
    }

}