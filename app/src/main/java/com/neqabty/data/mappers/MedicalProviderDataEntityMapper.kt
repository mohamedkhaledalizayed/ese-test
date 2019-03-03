package com.neqabty.data.mappers

import com.neqabty.data.entities.MedicalProviderData
import com.neqabty.domain.common.Mapper
import com.neqabty.domain.entities.MedicalProviderEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MedicalProviderDataEntityMapper @Inject constructor() : Mapper<MedicalProviderData, MedicalProviderEntity>() {

    override fun mapFrom(from: MedicalProviderData): MedicalProviderEntity {
        return MedicalProviderEntity(
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
                phones = from.phones,
                serviceProviderId = from.serviceProviderId
        )
    }
}
