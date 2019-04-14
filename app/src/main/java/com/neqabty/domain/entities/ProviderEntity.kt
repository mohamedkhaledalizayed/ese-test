package com.neqabty.domain.entities

import com.google.gson.annotations.SerializedName

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
        var type: String?
)