package com.neqabty.healthcare.modules.suggestions.data.model.complaintscategory


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
@Keep
data class Category(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name_ar")
    val nameAr: String,
    @SerializedName("name_en")
    val nameEn: String
)