package com.neqabty.presentation.mappers

import com.neqabty.domain.common.Mapper
import com.neqabty.domain.entities.ProviderEntity
import com.neqabty.presentation.entities.ProviderUI
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProviderUIEntityMapper @Inject constructor() : Mapper<ProviderUI, ProviderEntity>() {

    override fun mapFrom(from: ProviderUI): ProviderEntity {
        return ProviderEntity(
                id = from.id,
                address = from.address,
                name = from.name,
                governId = from.governId,
                updatedBy = from.updatedBy,
                createdBy = from.createdBy,
                createdAt = from.createdAt,
                updatedAt = from.updatedAt,
                areaId = from.areaId,
                emails = from.emails,
                phones = from.phones
        )
    }
}
