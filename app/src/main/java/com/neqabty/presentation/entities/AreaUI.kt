package com.neqabty.presentation.entities

data class AreaUI(
        var id: Int = 0,
        var name: String?
){
    override fun toString(): String {
        return name ?: ""
    }
}