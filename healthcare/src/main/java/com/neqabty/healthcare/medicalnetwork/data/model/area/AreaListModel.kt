package com.neqabty.healthcare.medicalnetwork.data.model.area


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class AreaListModel(
    @SerializedName("data")
    val `data`: List<AreaModel>,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean
)