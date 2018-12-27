package com.neqabty.data.mappers

import com.neqabty.data.entities.ProviderTypeData
import com.neqabty.domain.common.Mapper
import com.neqabty.domain.entities.ProviderTypeEntitiy
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProviderTypeDataEntityMapper @Inject constructor() : Mapper<ProviderTypeData, ProviderTypeEntitiy>() {

    override fun mapFrom(from: ProviderTypeData): ProviderTypeEntitiy {
        return ProviderTypeEntitiy(
                id = from.id,
                nameAr = from.nameAr,
                nameEn = from.nameEn,
                createdAt = from.createdAt,
                updatedAt = from.updatedAt
        )
    }
}
