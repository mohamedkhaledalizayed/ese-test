package com.neqabty.healthcare.commen.syndicateservices.domain.repository

import com.neqabty.healthcare.commen.syndicateservices.domain.entity.SyndicateServiceEntity
import kotlinx.coroutines.flow.Flow

interface SyndicateServicesRepository {
    fun getSyndicateServices(entityCode: String, serviceCategory: String): Flow<List<SyndicateServiceEntity>>
}