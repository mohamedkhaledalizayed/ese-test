package com.neqabty.presentation.mappers

import com.neqabty.domain.common.Mapper
import com.neqabty.domain.entities.ProviderTypeEntitiy
import com.neqabty.presentation.entities.ProviderTypeUI
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProviderTypeEntityUIMapper @Inject constructor() : Mapper<ProviderTypeEntitiy, ProviderTypeUI>() {

    override fun mapFrom(from: ProviderTypeEntitiy): ProviderTypeUI {
        return ProviderTypeUI(
                id = from.id,
                nameEn = from.nameEn,
                nameAr = from.nameAr,
                createdAt = from.createdAt,
                updatedAt = from.updatedAt
        )
    }
}
