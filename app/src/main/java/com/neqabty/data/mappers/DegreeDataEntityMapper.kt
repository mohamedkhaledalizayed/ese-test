package com.neqabty.data.mappers

import com.neqabty.data.entities.DegreeData
import com.neqabty.domain.common.Mapper
import com.neqabty.domain.entities.DegreeEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DegreeDataEntityMapper @Inject constructor() : Mapper<DegreeData, DegreeEntity>() {

    override fun mapFrom(from: DegreeData): DegreeEntity {
        return DegreeEntity(
                id = from.id,
                profession = from.profession
        )
    }
}
