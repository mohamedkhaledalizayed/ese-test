package com.neqabty.presentation.entities

data class ComplaintTypeUI(
    var id: Int = 0,
    var type: String?
) {
    override fun toString(): String {
        return type ?: ""
    }
}