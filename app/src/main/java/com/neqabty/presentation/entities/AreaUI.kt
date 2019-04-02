package com.neqabty.presentation.entities

data class AreaUI(
        var id: Int = 0,
        var name: String?,
        var govId: Int?
){
    override fun toString(): String {
        return name ?: ""
    }
}