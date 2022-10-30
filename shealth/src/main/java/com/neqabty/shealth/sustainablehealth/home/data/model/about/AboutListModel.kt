package com.neqabty.shealth.sustainablehealth.home.data.model.about


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
@Keep
data class AboutListModel(
    @SerializedName("data")
    val data: List<AboutModel>,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean
)