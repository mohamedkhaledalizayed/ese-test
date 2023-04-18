package com.neqabty.healthcare.chefaa.orders.data.model


import com.google.gson.annotations.SerializedName

data class PagingInfoModel(
    @SerializedName("current_page")
    val pageNumber: Int,
    @SerializedName("per_page")
    val pageSize: Int,
    @SerializedName("TotalCount")
    val totalCount: Int,
    @SerializedName("TotalNumberOfPages")
    val totalNumberOfPages: Int
)