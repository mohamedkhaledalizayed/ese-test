package com.neqabty.healthcare.packages.subscription.data.repository

import com.neqabty.healthcare.packages.subscription.data.datasource.SubscriptionSource
import com.neqabty.healthcare.packages.subscription.data.model.SubscribePostBodyRequest
import com.neqabty.healthcare.packages.subscription.data.model.SubscriptionModel
import com.neqabty.healthcare.packages.subscription.domain.entity.SubscriptionEntity
import com.neqabty.healthcare.packages.subscription.domain.repository.SubscriptionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SubscriptionRepositoryImpl @Inject constructor(private val subscriptionSource: SubscriptionSource) :
    SubscriptionRepository {

    override fun addSubscription(
        subscribePostBodyRequest: SubscribePostBodyRequest
    ): Flow<SubscriptionEntity> {
        return flow {
            emit(
                subscriptionSource.addSubscription(subscribePostBodyRequest).toSubscriptionEntity()
            )
        }
    }

}

private fun SubscriptionModel.toSubscriptionEntity(): SubscriptionEntity{
    return SubscriptionEntity(
        status = status,
        message = message
    )
}