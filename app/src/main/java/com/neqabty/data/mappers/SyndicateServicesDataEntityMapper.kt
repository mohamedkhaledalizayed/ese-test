package com.neqabty.data.mappers

import com.neqabty.data.entities.SyndicateServicesData
import com.neqabty.domain.common.Mapper
import com.neqabty.domain.entities.SyndicateServicesEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SyndicateServicesDataEntityMapper @Inject constructor() : Mapper<SyndicateServicesData, SyndicateServicesEntity>() {

    override fun mapFrom(from: SyndicateServicesData): SyndicateServicesEntity {
        val syndicateServicesEntity = SyndicateServicesEntity(null,null)

        from.typesList?.let{
            var types: List<SyndicateServicesEntity.ServiceType> = it.map { type ->
                return@map SyndicateServicesEntity.ServiceType(id = type.id, type = type.type)
            }
            syndicateServicesEntity.typesList = types
        }


        from.servicesList?.let{
            var services: List<SyndicateServicesEntity.Service> = it.map { service ->
                return@map SyndicateServicesEntity.Service(id = service.id, groupID = service.groupID, name = service.name, price = service.price)
            }
            syndicateServicesEntity.servicesList = services
        }

        return syndicateServicesEntity
    }
}
