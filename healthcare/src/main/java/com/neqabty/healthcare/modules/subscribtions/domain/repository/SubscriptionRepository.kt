package com.neqabty.healthcare.modules.subscribtions.domain.repository

import com.neqabty.healthcare.modules.subscribtions.data.model.SubscribePostBodyRequest
import com.neqabty.healthcare.modules.subscribtions.domain.entity.relations.RelationEntity
import com.neqabty.healthcare.modules.subscribtions.domain.entity.subscribtions.SubscriptionEntity
import kotlinx.coroutines.flow.Flow

interface SubscriptionRepository {
    fun getRelations(): Flow<List<RelationEntity>>
    fun addSubscription(subscribePostBodyRequest: SubscribePostBodyRequest): Flow<SubscriptionEntity>
}