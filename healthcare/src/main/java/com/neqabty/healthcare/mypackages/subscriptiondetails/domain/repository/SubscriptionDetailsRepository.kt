package com.neqabty.healthcare.mypackages.subscriptiondetails.domain.repository


import kotlinx.coroutines.flow.Flow

interface SubscriptionDetailsRepository {
    fun deleteFollower(followerId: Int, mobile: String, subscriberId: String): Flow<Boolean>
}