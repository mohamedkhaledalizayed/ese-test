package com.neqabty.healthcare.modules.suggestions.data.model.complaints


import com.google.gson.annotations.SerializedName

data class ComplaintModel(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean
)