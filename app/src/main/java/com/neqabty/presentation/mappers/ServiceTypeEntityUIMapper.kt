package com.neqabty.presentation.mappers

import com.neqabty.domain.common.Mapper
import com.neqabty.domain.entities.ServiceEntity
import com.neqabty.domain.entities.ServiceTypeEntity
import com.neqabty.presentation.entities.ServiceTypeUI
import com.neqabty.presentation.entities.ServiceUI
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ServiceTypeEntityUIMapper @Inject constructor() : Mapper<ServiceTypeEntity, ServiceTypeUI>() {

    override fun mapFrom(from: ServiceTypeEntity): ServiceTypeUI {
        return ServiceTypeUI(
                id = from.id,
                type = from.type
        )
    }
}
