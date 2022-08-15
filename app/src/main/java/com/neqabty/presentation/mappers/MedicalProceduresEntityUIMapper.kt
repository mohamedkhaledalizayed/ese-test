package com.neqabty.presentation.mappers

import com.neqabty.domain.common.Mapper
import com.neqabty.domain.entities.MedicalProcedureEntity
import com.neqabty.presentation.entities.MedicalProcedureUI
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MedicalProceduresEntityUIMapper @Inject constructor() : Mapper<MedicalProcedureEntity, MedicalProcedureUI>() {

    override fun mapFrom(from: MedicalProcedureEntity): MedicalProcedureUI {
        return MedicalProcedureUI(
            id = from.id,
            name = from.name,
            categoryId = from.categoryId
        )
    }
}