package com.neqabty.presentation.mappers

import com.neqabty.domain.common.Mapper
import com.neqabty.domain.entities.MedicalDirectoryProviderEntity
import com.neqabty.presentation.entities.MedicalDirectoryProviderUI
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MedicalDirectoryProviderEntityUIMapper @Inject constructor() : Mapper<MedicalDirectoryProviderEntity, MedicalDirectoryProviderUI>() {

    override fun mapFrom(from: MedicalDirectoryProviderEntity): MedicalDirectoryProviderUI {
        return MedicalDirectoryProviderUI(
                id = from.id,
                name = from.name,
                address = from.address,
                phone = from.phone,
                email = from.email
        )
    }
}
