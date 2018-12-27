package com.neqabty.presentation.entities

data class ProviderTypeUI(
        var id: Int = 0,
        var nameAr: String?,
        var nameEn: String?,
        var createdAt: String?,
        var updatedAt: String?
){
    override fun toString(): String {
        return nameAr ?: ""
    }
}