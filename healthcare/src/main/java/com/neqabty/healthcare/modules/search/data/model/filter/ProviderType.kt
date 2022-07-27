package com.neqabty.healthcare.modules.search.data.model.filter


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
@Keep
data class ProviderType(
    @SerializedName("id")
    val id: Int,
    @SerializedName("provider_type_ar")
    val providerTypeAr: String
)