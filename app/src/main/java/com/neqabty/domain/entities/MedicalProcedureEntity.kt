package com.neqabty.domain.entities

data class MedicalProcedureEntity(
    var id: Int = 0,
    var name: String?,
    var categoryId: Int
)