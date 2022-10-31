package com.neqabty.chefaa.modules.orders.data.model

import com.google.gson.annotations.SerializedName

data class OrderListResponse(
    @SerializedName("data") val orders: List<OrderModel>
//    @SerializedName("data") val pagingInfoModel: PagingInfoModel
)
