package com.neqabty.healthcare.syndicateservices.domain.repository

import com.neqabty.healthcare.syndicateservices.domain.entity.SyndicateServiceEntity
import kotlinx.coroutines.flow.Flow

interface SyndicateServicesRepository {
    fun getSyndicateServices(entityCode: String, serviceCategory: String): Flow<List<SyndicateServiceEntity>>
}