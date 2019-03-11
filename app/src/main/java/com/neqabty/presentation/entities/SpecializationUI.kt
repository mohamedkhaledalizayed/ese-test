package com.neqabty.presentation.entities

data class SpecializationUI(
        var id: Int = 0,
        var profession: String?
){
    override fun toString(): String {
        return profession ?: ""
    }
}