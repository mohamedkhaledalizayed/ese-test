package com.neqabty.yodawy.modules.order.data.model

import com.google.gson.annotations.SerializedName

data class OrderListRequestBody(
    @SerializedName("mobile") val mobile: String,
    @SerializedName("PageNumber") val pageNumber: Int = 0,
    @SerializedName("PageSize") val pageSize: Int = 5
)
