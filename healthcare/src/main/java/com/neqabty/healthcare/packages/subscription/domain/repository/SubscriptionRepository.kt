package com.neqabty.healthcare.packages.subscription.domain.repository

import com.neqabty.healthcare.packages.subscription.data.model.SubscribePostBodyRequest
import com.neqabty.healthcare.packages.subscription.domain.entity.SubscriptionEntity
import kotlinx.coroutines.flow.Flow

interface SubscriptionRepository {
    fun addSubscription(subscribePostBodyRequest: SubscribePostBodyRequest): Flow<SubscriptionEntity>
}