package com.neqabty.healthcare.modules.search.data.model


import com.google.gson.annotations.SerializedName
import java.util.*

data class ServiceProviderType(
    @SerializedName("created_at")
    val createdAt: Date? = null ,
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("points")
    val points: String? = null,
    @SerializedName("provider_type_ar")
    val providerTypeAr: String = "",
    @SerializedName("provider_type_en")
    val providerTypeEn: String = "",
    @SerializedName("updated_at")
    val updatedAt: Date? = null
)