package com.neqabty.data.mappers

import com.neqabty.data.entities.ComplaintTypeData
import com.neqabty.domain.common.Mapper
import com.neqabty.domain.entities.ComplaintTypeEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ComplaintTypeDataEntityMapper @Inject constructor() : Mapper<ComplaintTypeData, ComplaintTypeEntity>() {

    override fun mapFrom(from: ComplaintTypeData): ComplaintTypeEntity {
        return ComplaintTypeEntity(
                id = from.id,
                type = from.type
        )
    }
}
