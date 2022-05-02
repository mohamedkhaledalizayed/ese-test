package com.neqabty.data.mappers

import com.neqabty.data.entities.ServiceTypeData
import com.neqabty.domain.common.Mapper
import com.neqabty.domain.entities.ServiceTypeEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ServiceTypeDataEntityMapper @Inject constructor() : Mapper<ServiceTypeData, ServiceTypeEntity>() {

    override fun mapFrom(from: ServiceTypeData): ServiceTypeEntity {
        val serviceTypeEntity = ServiceTypeEntity(null,null)

        from.typesList?.let{
            var types: List<ServiceTypeEntity.ServiceType> = it.map { type ->
                return@map ServiceTypeEntity.ServiceType(id = type.id, type = type.type)
            }
            serviceTypeEntity.typesList = types
        }


        from.servicesList?.let{
            var services: List<ServiceTypeEntity.Service> = it.map { service ->
                return@map ServiceTypeEntity.Service(id = service.id, groupID = service.groupID, name = service.name, price = service.price)
            }
            serviceTypeEntity.servicesList = services
        }

        return serviceTypeEntity
    }
}
