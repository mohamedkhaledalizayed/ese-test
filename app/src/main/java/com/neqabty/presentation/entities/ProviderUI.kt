package com.neqabty.presentation.entities

data class ProviderUI(
        var id: Int = 0,
        var name: String?,
        var governId: String?,
        var areaId: String?,
        var address: String?,
        var phones: String?,
        var emails: String?,
        var createdBy: String?,
        var updatedBy: String?,
        var createdAt: String?,
        var updatedAt: String?
){
    override fun toString(): String {
        return name ?: ""
    }
}