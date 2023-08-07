package com.neqabty.healthcare.medicalnetwork.data.model.search


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
@Keep
data class ProvidersResponse(
    @SerializedName("data")
    val data: Data,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean
)