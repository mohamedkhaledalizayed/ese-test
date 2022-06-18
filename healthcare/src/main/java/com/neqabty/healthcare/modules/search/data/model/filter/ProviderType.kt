package com.neqabty.healthcare.modules.search.data.model.filter


import com.google.gson.annotations.SerializedName

data class ProviderType(
    @SerializedName("id")
    val id: Int,
    @SerializedName("provider_type_ar")
    val providerTypeAr: String
)