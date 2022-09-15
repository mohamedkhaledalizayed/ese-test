package com.neqabty.presentation.mappers

import com.neqabty.domain.common.Mapper
import com.neqabty.domain.entities.SyndicateServicesEntity
import com.neqabty.presentation.entities.SyndicateServicesUI
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SyndicateServicesEntityUIMapper @Inject constructor() : Mapper<SyndicateServicesEntity, SyndicateServicesUI>() {

    override fun mapFrom(from: SyndicateServicesEntity): SyndicateServicesUI {
        val syndicateServicesUI = SyndicateServicesUI(null,null)

        from.typesList?.let{
            var types: List<SyndicateServicesUI.ServiceType> = it.map { type ->
                return@map SyndicateServicesUI.ServiceType(id = type.id, type = type.type)
            }
            syndicateServicesUI.typesList = types
        }


        from.servicesList?.let{
            var services: List<SyndicateServicesUI.Service> = it.map { service ->
                return@map SyndicateServicesUI.Service(id = service.id, groupID = service.groupID, name = service.name, price = service.price)
            }
            syndicateServicesUI.servicesList = services
        }

        return syndicateServicesUI
    }
}