package com.neqabty.presentation.mappers

import com.neqabty.domain.common.Mapper
import com.neqabty.domain.entities.ServiceTypeEntity
import com.neqabty.presentation.entities.ServiceTypeUI
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ServiceTypeEntityUIMapper @Inject constructor() : Mapper<ServiceTypeEntity, ServiceTypeUI>() {

    override fun mapFrom(from: ServiceTypeEntity): ServiceTypeUI {
        val serviceTypeUI = ServiceTypeUI(null,null)

        from.typesList?.let{
            var types: List<ServiceTypeUI.ServiceType> = it.map { type ->
                return@map ServiceTypeUI.ServiceType(id = type.id, type = type.type)
            }
            serviceTypeUI.typesList = types
        }


        from.servicesList?.let{
            var services: List<ServiceTypeUI.Service> = it.map { service ->
                return@map ServiceTypeUI.Service(id = service.id, groupID = service.groupID, name = service.name, price = service.price)
            }
            serviceTypeUI.servicesList = services
        }

        return serviceTypeUI
    }
}