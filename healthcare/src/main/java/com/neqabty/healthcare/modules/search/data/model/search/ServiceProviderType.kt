package com.neqabty.healthcare.modules.search.data.model.search


import com.google.gson.annotations.SerializedName

data class ServiceProviderType(
    @SerializedName("id")
    val id: Int,
    @SerializedName("provider_type_ar")
    val providerTypeAr: String,
    @SerializedName("provider_type_en")
    val providerTypeEn: String
)