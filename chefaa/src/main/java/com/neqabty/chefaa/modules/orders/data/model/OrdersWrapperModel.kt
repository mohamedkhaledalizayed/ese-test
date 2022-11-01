package com.neqabty.chefaa.modules.orders.data.model

import com.google.gson.annotations.SerializedName

data class OrdersWrapperModel<T>(
    @SerializedName("current_page")
    val currentPage: Int? = null,
    @SerializedName("first_page_url")
    val firstPageUrl: String? = null,
    @SerializedName("from")
    val from: Int? = null,
    @SerializedName("next_page_url")
    val nextPageUrl: String? = null,
    @SerializedName("path")
    val path: String? = null,
    @SerializedName("per_page")
    val perPage: Int? = null,
    @SerializedName("prev_page_url")
    val prevPageUrl: String? = null,
    @SerializedName("to")
    val to: Int? = null,
    @SerializedName("data")
    val dataModels: T
)
