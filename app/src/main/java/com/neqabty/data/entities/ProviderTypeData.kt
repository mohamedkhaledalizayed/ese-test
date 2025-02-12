package com.neqabty.data.entities
import com.google.gson.annotations.SerializedName
import com.neqabty.data.api.Response

data class ProviderTypeData(
    @field:SerializedName("id")
    var id: Int = 0,
    @field:SerializedName("provider_type_ar")
    var nameAr: String?,
    @field:SerializedName("provider_type_en")
    var nameEn: String?,
    @field:SerializedName("created_at")
    var createdAt: String?,
    @field:SerializedName("updated_at")
    var updatedAt: String?
) : Response()