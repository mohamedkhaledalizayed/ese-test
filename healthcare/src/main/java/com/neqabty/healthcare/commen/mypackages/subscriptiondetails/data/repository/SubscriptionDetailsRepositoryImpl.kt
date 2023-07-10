package com.neqabty.healthcare.commen.mypackages.subscriptiondetails.data.repository

import com.neqabty.healthcare.commen.mypackages.subscriptiondetails.data.datasource.SubscriptionDetailsDS
import com.neqabty.healthcare.commen.mypackages.subscriptiondetails.data.model.DeleteFollowerBody
import com.neqabty.healthcare.commen.mypackages.subscriptiondetails.domain.repository.SubscriptionDetailsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SubscriptionDetailsRepositoryImpl @Inject constructor(private val subscriptionDetailsDS: SubscriptionDetailsDS) :
    SubscriptionDetailsRepository {

    override fun deleteFollower(followerId: Int, mobile: String, subscriberId: String): Flow<Boolean> {
        return flow {
            emit(subscriptionDetailsDS.deleteFollower(DeleteFollowerBody(followerId, mobile, subscriberId)))
        }
    }

}