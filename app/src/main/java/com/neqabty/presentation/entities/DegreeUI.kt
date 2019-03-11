package com.neqabty.presentation.entities

data class DegreeUI(
        var id: Int = 0,
        var profession: String?
){
    override fun toString(): String {
        return profession ?: ""
    }
}