package com.neqabty.healthcare.suggestions.data.model.complaintscategory


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
@Keep
data class Package(
    @SerializedName("id")
    val id: String,
    @SerializedName("name_ar")
    val nameAr: String,
    @SerializedName("name_en")
    val nameEn: Any
)