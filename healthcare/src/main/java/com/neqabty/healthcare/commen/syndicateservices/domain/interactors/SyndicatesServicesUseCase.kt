package com.neqabty.healthcare.commen.syndicateservices.domain.interactors


import com.neqabty.healthcare.commen.syndicateservices.domain.entity.SyndicateServiceEntity
import com.neqabty.healthcare.commen.syndicateservices.domain.repository.SyndicateServicesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SyndicatesServicesUseCase @Inject constructor(private val syndicateServicesRepository: SyndicateServicesRepository) {
    fun build(entityCode: String, serviceCategory: String): Flow<List<SyndicateServiceEntity>> {
        return syndicateServicesRepository.getSyndicateServices(entityCode, serviceCategory)
    }
}