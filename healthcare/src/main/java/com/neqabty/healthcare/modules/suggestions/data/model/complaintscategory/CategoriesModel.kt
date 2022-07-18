package com.neqabty.healthcare.modules.suggestions.data.model.complaintscategory


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
@Keep
data class CategoriesModel(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean
)