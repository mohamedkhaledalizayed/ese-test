package com.neqabty.healthcare.sustainablehealth.search.data.model.search


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
@Keep
data class ServiceProviderType(
    @SerializedName("id")
    val id: Int,
    @SerializedName("provider_type_ar")
    val providerTypeAr: String,
    @SerializedName("provider_type_en")
    val providerTypeEn: String?
)