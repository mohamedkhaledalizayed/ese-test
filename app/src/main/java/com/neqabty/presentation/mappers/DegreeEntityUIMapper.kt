package com.neqabty.presentation.mappers

import com.neqabty.domain.common.Mapper
import com.neqabty.domain.entities.DegreeEntity
import com.neqabty.presentation.entities.DegreeUI
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DegreeEntityUIMapper @Inject constructor() : Mapper<DegreeEntity, DegreeUI>() {

    override fun mapFrom(from: DegreeEntity): DegreeUI {
        return DegreeUI(
                id = from.id,
                code = from.code,
                profession = from.profession
        )
    }
}
