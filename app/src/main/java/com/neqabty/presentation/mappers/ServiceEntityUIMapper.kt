package com.neqabty.presentation.mappers

import com.neqabty.domain.common.Mapper
import com.neqabty.domain.entities.ServiceEntity
import com.neqabty.presentation.entities.ServiceUI
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ServiceEntityUIMapper @Inject constructor() : Mapper<ServiceEntity, ServiceUI>() {

    override fun mapFrom(from: ServiceEntity): ServiceUI {
        return ServiceUI(
                id = from.id,
                name = from.name,
                cost = from.cost
        )
    }
}
