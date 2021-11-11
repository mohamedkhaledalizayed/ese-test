package com.neqabty.yodawy.modules.order.data.model


import com.google.gson.annotations.SerializedName

data class PagingInfoModel(
    @SerializedName("PageNumber")
    val pageNumber: Int,
    @SerializedName("PageSize")
    val pageSize: Int,
    @SerializedName("TotalCount")
    val totalCount: Int,
    @SerializedName("TotalNumberOfPages")
    val totalNumberOfPages: Int
)