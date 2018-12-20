package com.neqabty.presentation.entities

data class SpecializationUI(
        var id: Int = 0,
        var code: String?,
        var profession: String?
){
    override fun toString(): String {
        return profession ?: ""
    }
}