package com.neqabty.domain.entities

data class ProviderEntity(
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
    var updatedAt: String?,
    var typeID: String?,
    var typeName: String?,
    var providerId: String?,
    var branchProfileId: String?
)