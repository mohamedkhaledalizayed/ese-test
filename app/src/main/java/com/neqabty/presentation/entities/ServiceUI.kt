package com.neqabty.presentation.entities

data class ServiceUI(
    var id: Int = 0,
    var name: String?
) {
    override fun toString(): String {
        return name ?: ""
    }
}