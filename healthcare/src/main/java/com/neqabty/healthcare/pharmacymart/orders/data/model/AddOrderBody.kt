package com.neqabty.healthcare.pharmacymart.orders.data.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class AddOrderBody(
    @SerializedName("address_id")
    val addressId:Int,
    @SerializedName("mobile")
    val mobile:String,
    @SerializedName("delivery_mobile")
    val deliveryMobile:String,
    @SerializedName("delivery_note")
    val deliverNote:String,
    @SerializedName("platform")
    val platform:String = "android",
    @SerializedName("device_info")
    val deviceInfo:String,
    @SerializedName("current_location")
    val currentLocation:String,
    @SerializedName("order_text")
    val orderByText:String,
    @SerializedName("attachments")
    val items: List<String>
)
