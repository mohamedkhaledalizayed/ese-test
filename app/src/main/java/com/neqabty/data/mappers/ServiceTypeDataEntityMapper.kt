package com.neqabty.data.mappers

import com.neqabty.data.entities.ServiceData
import com.neqabty.data.entities.ServiceTypeData
import com.neqabty.domain.common.Mapper
import com.neqabty.domain.entities.ServiceEntity
import com.neqabty.domain.entities.ServiceTypeEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ServiceTypeDataEntityMapper @Inject constructor() : Mapper<ServiceTypeData, ServiceTypeEntity>() {

    override fun mapFrom(from: ServiceTypeData): ServiceTypeEntity {
        return ServiceTypeEntity(
                id = from.id,
                type = from.type
        )
    }
}
