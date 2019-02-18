package com.neqabty.presentation.entities

data class AreaUI(
        var id: Int = 0,
        var code: Int?,
        var name: String?
){
    override fun toString(): String {
        return name ?: ""
    }
}