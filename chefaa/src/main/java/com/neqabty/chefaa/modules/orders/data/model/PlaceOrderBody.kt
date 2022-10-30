package com.neqabty.chefaa.modules.orders.data.model

import com.google.gson.annotations.SerializedName

data class PlaceOrderBody(
    @SerializedName("address_id")
    val addressId:Int,
    @SerializedName("phone")
    val phone:String,
    @SerializedName("delivery_note")
    val deliverNote:String,
    @SerializedName("items")
    val items: List<OrderItemModel>
)
