package com.neqabty.healthcare.sustainablehealth.subscribtions.domain.usecases


import com.neqabty.healthcare.sustainablehealth.subscribtions.domain.entity.terms.TermsEntityList
import com.neqabty.healthcare.sustainablehealth.subscribtions.domain.repository.SubscriptionRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTermsAndConditionUseCase @Inject constructor(private val subscriptionRepository: SubscriptionRepository) {
    fun build(id: String): Flow<TermsEntityList> {
        return subscriptionRepository.getTermsAndConditions(id)
    }
}