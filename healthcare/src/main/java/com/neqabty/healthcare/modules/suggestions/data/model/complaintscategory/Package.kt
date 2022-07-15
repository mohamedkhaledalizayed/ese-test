package com.neqabty.healthcare.modules.suggestions.data.model.complaintscategory


import com.google.gson.annotations.SerializedName

data class Package(
    @SerializedName("id")
    val id: String,
    @SerializedName("name_ar")
    val nameAr: String,
    @SerializedName("name_en")
    val nameEn: Any
)