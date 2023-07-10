package com.neqabty.healthcare.commen.packages.subscription.domain.usecases

import com.neqabty.healthcare.commen.packages.subscription.data.model.SubscribePostBodyRequest
import com.neqabty.healthcare.commen.packages.subscription.domain.entity.SubscriptionEntity
import com.neqabty.healthcare.commen.packages.subscription.domain.repository.SubscriptionRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AddSubscriptionUseCase @Inject constructor(private val subscriptionRepository: SubscriptionRepository) {

    fun build(subscribePostBodyRequest: SubscribePostBodyRequest): Flow<SubscriptionEntity> {
        return subscriptionRepository.addSubscription(subscribePostBodyRequest)
    }

}