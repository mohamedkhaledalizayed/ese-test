package com.neqabty.healthcare.modules.search.data.model.search


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("current_page")
    val currentPage: Int,
    @SerializedName("data")
    val data: List<ProvidersModel>,
    @SerializedName("first_page_url")
    val firstPageUrl: String?,
    @SerializedName("from")
    val from: Int,
    @SerializedName("next_page_url")
    val nextPageUrl: String?,
    @SerializedName("path")
    val path: String?,
    @SerializedName("per_page")
    val perPage: Int,
    @SerializedName("to")
    val to: Int
)