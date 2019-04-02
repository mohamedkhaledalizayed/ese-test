package com.neqabty.presentation.mappers

import com.neqabty.domain.common.Mapper
import com.neqabty.domain.entities.GovernEntity
import com.neqabty.presentation.entities.GovernUI
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GovernEntityUIMapper @Inject constructor() : Mapper<GovernEntity, GovernUI>() {

    override fun mapFrom(from: GovernEntity): GovernUI {
        return GovernUI(
                id = from.id,
                name = from.name
        )
    }
}
