package com.neqabty.healthcare.sustainablehealth.home.data.model.about.packages


import com.google.gson.annotations.SerializedName

data class PackagesListModel(
    @SerializedName("data")
    val data: List<PackageModel>,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean
)