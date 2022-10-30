package com.neqabty.shealth.sustainablehealth.suggestions.data.model.complaints


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
@Keep
data class ComplaintModel(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean
)