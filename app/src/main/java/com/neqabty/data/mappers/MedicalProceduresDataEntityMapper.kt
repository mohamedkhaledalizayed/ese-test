package com.neqabty.data.mappers

import com.neqabty.data.entities.MedicalProcedureData
import com.neqabty.domain.common.Mapper
import com.neqabty.domain.entities.MedicalProcedureEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MedicalProceduresDataEntityMapper @Inject constructor() : Mapper<MedicalProcedureData, MedicalProcedureEntity>() {

    override fun mapFrom(from: MedicalProcedureData): MedicalProcedureEntity {
        return MedicalProcedureEntity(
            id = from.id,
            name = from.name,
            categoryId = from.categoryId
        )
    }
}