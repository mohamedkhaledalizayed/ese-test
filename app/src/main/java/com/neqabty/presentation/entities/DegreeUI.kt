package com.neqabty.presentation.entities

data class DegreeUI(
        var id: Int = 0,
        var profession: String? = "اختار الكل"
){
    override fun toString(): String {
        return profession ?: ""
    }
}