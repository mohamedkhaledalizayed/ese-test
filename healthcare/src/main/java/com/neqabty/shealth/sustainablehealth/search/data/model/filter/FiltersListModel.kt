package com.neqabty.shealth.sustainablehealth.search.data.model.filter


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