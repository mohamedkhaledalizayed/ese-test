package com.neqabty.presentation.mappers

import com.neqabty.domain.common.Mapper
import com.neqabty.domain.entities.SpecializationEntity
import com.neqabty.presentation.entities.SpecializationUI
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SpecializationEntityUIMapper @Inject constructor() : Mapper<SpecializationEntity, SpecializationUI>() {

    override fun mapFrom(from: SpecializationEntity): SpecializationUI {
        return SpecializationUI(
                id = from.id,
                profession = from.profession
        )
    }
}
