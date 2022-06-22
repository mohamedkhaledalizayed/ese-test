package com.neqabty.healthcare.modules.search.data.model.packages


import com.google.gson.annotations.SerializedName

data class PackagesListModel(
    @SerializedName("data")
    val data: List<PackageModel>,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean
)