package com.neqabty.healthcare.syndicateservices.data.repository


import com.neqabty.healthcare.syndicateservices.data.datasource.SyndicateServicesDS
import com.neqabty.healthcare.syndicateservices.data.model.SyndicateService
import com.neqabty.healthcare.syndicateservices.domain.entity.Links
import com.neqabty.healthcare.syndicateservices.domain.entity.ServiceCategory
import com.neqabty.healthcare.syndicateservices.domain.entity.SyndicateServiceEntity
import com.neqabty.healthcare.syndicateservices.domain.repository.SyndicateServicesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SyndicateServicesRepositoryImpl @Inject constructor(private val syndicateServicesDS: SyndicateServicesDS): SyndicateServicesRepository {

    override fun getSyndicateServices(
        entityCode: String,
        serviceCategory: String
    ): Flow<List<SyndicateServiceEntity>> {
        return flow {
            emit(syndicateServicesDS.getSyndicateServices(entityCode, serviceCategory).map { it.toSyndicateServicesEntity() })
        }
    }

}

fun SyndicateService.toSyndicateServicesEntity(): SyndicateServiceEntity {
    return SyndicateServiceEntity(
        name, code, serviceCategory?.let { ServiceCategory(it.name) }, type, price, isActive, actions, Links(links.entity)
    )
}

