package com.neqabty.healthcare.packages.packageslist.data.model


import com.google.gson.annotations.SerializedName

data class PackagesListModel(
    @SerializedName("data")
    val data: List<PackageModel>,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean
)