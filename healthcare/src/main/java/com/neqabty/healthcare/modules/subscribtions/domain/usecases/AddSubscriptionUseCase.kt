package com.neqabty.healthcare.modules.subscribtions.domain.usecases

import com.neqabty.healthcare.modules.subscribtions.domain.repository.SubscriptionRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AddSubscriptionUseCase @Inject constructor(private val subscriptionRepository: SubscriptionRepository) {
    fun build(
        name: String,
        birthDate: String,
        email: String,
        address: String,
        job: String,
        mobile: String,
        nationalId: String,
        syndicateId: Int,
        packageId: String,
        referralNumber: String,
        personalImage: String,
        fronIdImage: String,
        backIdImage: String
    ): Flow<Boolean> {
        val p = personalImage
        return subscriptionRepository.addSubscription(
            name,
            birthDate,
            email,
            address,
            job,
            mobile,
            nationalId,
            syndicateId,
            packageId,
            referralNumber,
            personalImage,
            fronIdImage,
            backIdImage
        )
    }
}