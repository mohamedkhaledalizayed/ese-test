package com.neqabty.healthcare.sustainablehealth.subscribtions.domain.repository

import com.neqabty.healthcare.sustainablehealth.subscribtions.data.model.SubscribePostBodyRequest
import com.neqabty.healthcare.sustainablehealth.subscribtions.domain.entity.relations.RelationEntity
import com.neqabty.healthcare.sustainablehealth.subscribtions.domain.entity.subscribtions.SubscriptionEntity
import kotlinx.coroutines.flow.Flow

interface SubscriptionRepository {
    fun getRelations(): Flow<List<RelationEntity>>
    fun addSubscription(subscribePostBodyRequest: SubscribePostBodyRequest): Flow<SubscriptionEntity>
}