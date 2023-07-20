package com.neqabty.healthcare.medicalnetwork.data.model.filter


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
@Keep
data class FiltersListModel(
    @SerializedName("data")
    val data: FiltersModel,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean
)