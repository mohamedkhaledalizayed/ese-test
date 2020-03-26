package com.neqabty.data.mappers

import com.neqabty.data.entities.ServiceData
import com.neqabty.domain.common.Mapper
import com.neqabty.domain.entities.ServiceEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ServiceDataEntityMapper @Inject constructor() : Mapper<ServiceData, ServiceEntity>() {

    override fun mapFrom(from: ServiceData): ServiceEntity {
        return ServiceEntity(
                id = from.id,
                name = from.name
        )
    }
}
