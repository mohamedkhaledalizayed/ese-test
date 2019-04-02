package com.neqabty.data.mappers

import com.neqabty.data.entities.AreaData
import com.neqabty.domain.common.Mapper
import com.neqabty.domain.entities.AreaEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AreaDataEntityMapper @Inject constructor() : Mapper<AreaData, AreaEntity>() {

    override fun mapFrom(from: AreaData): AreaEntity {
        return AreaEntity(
                id = from.id,
                name = from.name,
                govId = from.govId
        )
    }
}
