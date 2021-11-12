package com.neqabty.yodawy.modules.orders.data.model

import com.google.gson.annotations.SerializedName

data class OrderListRequestBody(
    @SerializedName("mobile") val mobile: String,
    @SerializedName("PageNumber") val pageNumber: Int = 0,
    @SerializedName("PageSize") val pageSize: Int = 5
)
