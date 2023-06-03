package com.neqabty.healthcare.commen.complains.data.model


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class ComplainBody(
    @SerializedName("mobile")
    val mobile: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("entity")
    val entity: Int,
    @SerializedName("message")
    val message: String
)