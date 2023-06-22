package com.neqabty.healthcare.sustainablehealth.subscribtions.domain.repository

import com.neqabty.healthcare.sustainablehealth.subscribtions.data.model.SubscribePostBodyRequest
import com.neqabty.healthcare.sustainablehealth.subscribtions.data.model.UpdatePackageBody
import com.neqabty.healthcare.sustainablehealth.subscribtions.domain.entity.relations.RelationEntity
import com.neqabty.healthcare.sustainablehealth.subscribtions.domain.entity.subscribtions.SubscriptionEntity
import com.neqabty.healthcare.sustainablehealth.subscribtions.domain.entity.terms.TermsEntityList
import com.neqabty.healthcare.sustainablehealth.subscribtions.domain.entity.updatepackage.UpdatePackageEntity
import kotlinx.coroutines.flow.Flow

interface SubscriptionRepository {
    fun getRelations(): Flow<List<RelationEntity>>
    fun addSubscription(subscribePostBodyRequest: SubscribePostBodyRequest): Flow<SubscriptionEntity>
    fun updatePackage(updatePackageBody: UpdatePackageBody): Flow<UpdatePackageEntity>
    fun getTermsAndConditions(id: String): Flow<TermsEntityList>
}