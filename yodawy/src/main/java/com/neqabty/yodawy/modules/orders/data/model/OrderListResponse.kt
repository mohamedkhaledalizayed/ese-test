package com.neqabty.yodawy.modules.orders.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class OrderListResponse(
    @SerializedName("Data") val orders: List<OrderModel>,
    @SerializedName("PagingInfo") val pagingInfoModel: PagingInfoModel
): Parcelable
