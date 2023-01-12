package com.neqabty.healthcare.sustainablehealth.search.data.model.area


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class AreaListModel(
    @SerializedName("data")
    val `data`: List<AreaModel>,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean
)