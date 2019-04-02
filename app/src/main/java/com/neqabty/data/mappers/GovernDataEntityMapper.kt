package com.neqabty.data.mappers

import com.neqabty.data.entities.GovernData
import com.neqabty.domain.common.Mapper
import com.neqabty.domain.entities.GovernEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GovernDataEntityMapper @Inject constructor() : Mapper<GovernData, GovernEntity>() {

    override fun mapFrom(from: GovernData): GovernEntity {
        return GovernEntity(
                id = from.id,
                name = from.name
        )
    }
}
