package com.neqabty.presentation.mappers

import com.neqabty.domain.common.Mapper
import com.neqabty.domain.entities.MedicalProviderEntity
import com.neqabty.presentation.entities.MedicalProviderUI
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MedicalProviderEntityUIMapper @Inject constructor() : Mapper<MedicalProviderEntity, MedicalProviderUI>() {

    override fun mapFrom(from: MedicalProviderEntity): MedicalProviderUI {
        return MedicalProviderUI(
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
