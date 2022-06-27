package com.neqabty.healthcare.modules.subscribtions.domain.repository

import com.neqabty.healthcare.modules.subscribtions.data.model.Followers
import kotlinx.coroutines.flow.Flow
import java.net.URI

interface SubscriptionRepository {
    fun addSubscription(
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
        backIdImage: String,
        followers: List<Followers>
    ): Flow<Boolean>
}