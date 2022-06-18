package com.neqabty.healthcare.modules.search.data.model.filter


import com.google.gson.annotations.SerializedName

data class FiltersListModel(
    @SerializedName("data")
    val data: FiltersModel,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean
)