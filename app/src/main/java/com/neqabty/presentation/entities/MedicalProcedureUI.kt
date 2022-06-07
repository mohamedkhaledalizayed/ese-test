package com.neqabty.presentation.entities

data class MedicalProcedureUI(
    var id: Int = 0,
    var name: String?,
    var categoryId: Int
){
    override fun toString(): String {
        return name ?: ""
    }
}