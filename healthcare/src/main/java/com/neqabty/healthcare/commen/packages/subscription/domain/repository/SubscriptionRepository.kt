package com.neqabty.healthcare.commen.packages.subscription.domain.repository

import com.neqabty.healthcare.commen.packages.subscription.data.model.SubscribePostBodyRequest
import com.neqabty.healthcare.commen.packages.subscription.domain.entity.SubscriptionEntity
import kotlinx.coroutines.flow.Flow

interface SubscriptionRepository {
    fun addSubscription(subscribePostBodyRequest: SubscribePostBodyRequest): Flow<SubscriptionEntity>
}