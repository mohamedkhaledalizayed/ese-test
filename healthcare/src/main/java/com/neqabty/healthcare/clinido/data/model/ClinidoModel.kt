package com.neqabty.healthcare.clinido.data.model


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class ClinidoModel(
    @SerializedName("data")
    val `data`: Data?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("status")
    val status: Boolean?,
    @SerializedName("status_code")
    val status_code: Int?
)