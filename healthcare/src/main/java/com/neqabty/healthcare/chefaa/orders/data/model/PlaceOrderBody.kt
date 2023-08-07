package com.neqabty.healthcare.chefaa.orders.data.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class PlaceOrderBody(
    @SerializedName("address_id")
    val addressId:Int,
    @SerializedName("phone")
    val phone:String,
    @SerializedName("delivery_note")
    val deliverNote:String,
    @SerializedName("platform")
    val platform:String = "android",
    @SerializedName("device_info")
    val deviceInfo:String,
    @SerializedName("current_location")
    val currentLocation:String,
    @SerializedName("items")
    val items: List<OrderItemModel>
)
