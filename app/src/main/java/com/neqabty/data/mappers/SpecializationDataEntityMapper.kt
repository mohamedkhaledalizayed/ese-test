package com.neqabty.data.mappers

import com.neqabty.data.entities.DoctorData
import com.neqabty.data.entities.SpecializationData
import com.neqabty.domain.common.Mapper
import com.neqabty.domain.entities.DoctorEntity
import com.neqabty.domain.entities.SpecializationEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SpecializationDataEntityMapper @Inject constructor() : Mapper<SpecializationData, SpecializationEntity>() {

    override fun mapFrom(from: SpecializationData): SpecializationEntity {
        return SpecializationEntity(
                id = from.id,
                code = from.code,
                profession = from.profession
        )
    }
}
