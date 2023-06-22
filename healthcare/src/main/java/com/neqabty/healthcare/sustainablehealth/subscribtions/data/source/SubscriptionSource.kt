package com.neqabty.healthcare.sustainablehealth.subscribtions.data.source


import com.neqabty.healthcare.core.data.PreferencesHelper
import com.neqabty.healthcare.sustainablehealth.subscribtions.data.api.SubscriptionApi
import com.neqabty.healthcare.sustainablehealth.subscribtions.data.model.SubscribePostBodyRequest
import com.neqabty.healthcare.sustainablehealth.subscribtions.data.model.TermsBody
import com.neqabty.healthcare.sustainablehealth.subscribtions.data.model.UpdatePackageBody
import com.neqabty.healthcare.sustainablehealth.subscribtions.data.model.relationstypes.Relation
import com.neqabty.healthcare.sustainablehealth.subscribtions.data.model.subscription.SubscriptionModel
import com.neqabty.healthcare.sustainablehealth.subscribtions.data.model.terms.TermsModel
import com.neqabty.healthcare.sustainablehealth.subscribtions.data.model.updatepackage.UpdatePackageModel
import javax.inject.Inject

class SubscriptionSource @Inject constructor(private val subscriptionApi: SubscriptionApi, private val sharedPreferences: PreferencesHelper) {

    suspend fun getRelations(): List<Relation>{
        return subscriptionApi.getRelations().data.relations
    }

    suspend fun addSubscription(subscribePostBodyRequest: SubscribePostBodyRequest): SubscriptionModel {

        return subscriptionApi.addSubscription(
            token = "Token ${sharedPreferences.token}",
            subscribePostBodyRequest = subscribePostBodyRequest
        )
    }

    suspend fun updatePackage(updatePackageBody: UpdatePackageBody): UpdatePackageModel {
        return subscriptionApi.updatePackage(token = "Token ${sharedPreferences.token}", updatePackageBody = updatePackageBody)
    }

    suspend fun getTermsAndConditions(id: String): TermsModel {
        return subscriptionApi.getTermsAndConditions(TermsBody(package_id = id))
    }
}