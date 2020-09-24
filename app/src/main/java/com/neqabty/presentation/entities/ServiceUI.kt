package com.neqabty.presentation.entities

data class ServiceUI(
    var id: Int = 0,
    var name: String?,
    var cost: Int? = 0
) {
    override fun toString(): String {
        return name ?: ""
    }
}