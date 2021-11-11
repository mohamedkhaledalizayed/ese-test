package com.neqabty.yodawy.modules.order.data.model

import com.google.gson.annotations.SerializedName

data class OrderListResponse(
    @SerializedName("Data") val orders: List<OrderModel>,
    @SerializedName("PagingInfo") val pagingInfoModel: PagingInfoModel
)
