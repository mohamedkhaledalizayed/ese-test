package com.neqabty.presentation.mappers

import com.neqabty.domain.common.Mapper
import com.neqabty.domain.entities.AreaEntity
import com.neqabty.presentation.entities.AreaUI
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AreaEntityUIMapper @Inject constructor() : Mapper<AreaEntity, AreaUI>() {

    override fun mapFrom(from: AreaEntity): AreaUI {
        return AreaUI(
                id = from.id,
                name = from.name
        )
    }
}
