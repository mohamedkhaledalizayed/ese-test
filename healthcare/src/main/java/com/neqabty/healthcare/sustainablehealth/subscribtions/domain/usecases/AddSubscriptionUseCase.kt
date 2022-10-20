package com.neqabty.healthcare.sustainablehealth.subscribtions.domain.usecases

import com.neqabty.healthcare.sustainablehealth.subscribtions.data.model.SubscribePostBodyRequest
import com.neqabty.healthcare.sustainablehealth.subscribtions.domain.entity.relations.RelationEntity
import com.neqabty.healthcare.sustainablehealth.subscribtions.domain.entity.subscribtions.SubscriptionEntity
import com.neqabty.healthcare.sustainablehealth.subscribtions.domain.repository.SubscriptionRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AddSubscriptionUseCase @Inject constructor(private val subscriptionRepository: SubscriptionRepository) {
    fun build(): Flow<List<RelationEntity>>{
        return subscriptionRepository.getRelations()
    }

    fun build(
        subscribePostBodyRequest: SubscribePostBodyRequest
    ): Flow<SubscriptionEntity> {
        return subscriptionRepository.addSubscription(subscribePostBodyRequest)
    }
}