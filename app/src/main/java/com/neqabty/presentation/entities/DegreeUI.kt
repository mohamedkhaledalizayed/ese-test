package com.neqabty.presentation.entities

data class DegreeUI(
        var id: Int = 0,
        var code: String?,
        var profession: String?
){
    override fun toString(): String {
        return profession ?: ""
    }
}