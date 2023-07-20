package com.neqabty.healthcare.mypackages.subscriptiondetails.domain.usecases

import com.neqabty.healthcare.mypackages.subscriptiondetails.domain.repository.SubscriptionDetailsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeleteFollowerUseCase @Inject constructor(private val subscriptionDetailsRepository: SubscriptionDetailsRepository) {

    fun build(followerId: Int, mobile: String, subscriberId: String): Flow<Boolean>{
        return subscriptionDetailsRepository.deleteFollower(followerId, mobile, subscriberId)
    }

}