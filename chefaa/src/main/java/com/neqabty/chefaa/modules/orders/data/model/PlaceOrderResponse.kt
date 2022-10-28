package com.neqabty.chefaa.modules.orders.data.model


import com.google.gson.annotations.SerializedName

data class PlaceOrderResponse(
    @SerializedName("address_id")
    val addressId: Int = 0,
    @SerializedName("chefaa_order_number")
    val chefaaOrderNumber: String = "",
    @SerializedName("country_code")
    val countryCode: String = "",
    @SerializedName("created_at")
    val createdAt: String = "",
    @SerializedName("delivery_note")
    val deliveryNote: String = "",
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("phone")
    val phone: String = "",
    @SerializedName("updated_at")
    val updatedAt: String = "",
    @SerializedName("user_id")
    val userId: Int = 0,
    @SerializedName("user_number")
    val userNumber: String = "",
    @SerializedName("user_plan")
    val userPlan: String = ""
)