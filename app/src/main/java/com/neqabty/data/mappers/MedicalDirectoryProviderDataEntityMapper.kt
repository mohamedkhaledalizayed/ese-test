package com.neqabty.data.mappers

import com.neqabty.data.entities.MedicalDirectoryProviderData
import com.neqabty.domain.common.Mapper
import com.neqabty.domain.entities.MedicalDirectoryProviderEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MedicalDirectoryProviderDataEntityMapper @Inject constructor() : Mapper<MedicalDirectoryProviderData, MedicalDirectoryProviderEntity>() {

    override fun mapFrom(from: MedicalDirectoryProviderData): MedicalDirectoryProviderEntity {
        return MedicalDirectoryProviderEntity(
                id = from.id,
                name = from.name ?: "",
                address = from.address ?: "",
                phone = from.phone ?: "",
                email = from.email ?: ""
        )
    }
}
