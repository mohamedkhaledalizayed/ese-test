package com.neqabty.healthcare.modules.home.data.model.about


import com.google.gson.annotations.SerializedName

data class AboutListModel(
    @SerializedName("data")
    val data: List<AboutModel>,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean
)