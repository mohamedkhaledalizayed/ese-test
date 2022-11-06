package com.neqabty.chefaa.modules.orders.data.model


import com.google.gson.annotations.SerializedName

data class OrderModel(
    @SerializedName("address_id")
    val addressId: Int = 0,
    @SerializedName("chefaa_order_number")
    val chefaaOrderNumber: String = "",
    @SerializedName("client_id")
    val clientId: String = "",
    @SerializedName("country_code")
    val countryCode: String = "",
    @SerializedName("created_at")
    val createdAt: String = "",
    @SerializedName("delivery_fees")
    val deliveryFees: Int? = null,
    @SerializedName("delivery_note")
    val deliveryNote: String = "",
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("order_client")
    val orderClient: OrderClient = OrderClient(),
    @SerializedName("order_status")
    val orderStatus: OrderStatus = OrderStatus(),
    @SerializedName("phone")
    val phone: String = "",
    @SerializedName("price")
    val price: Int? = null,
    @SerializedName("price_before_discount")
    val priceBeforeDiscount: Int? = null,
    @SerializedName("status")
    val status: Int = 0,
    @SerializedName("updated_at")
    val updatedAt: String = "",
    @SerializedName("user_plan")
    val userPlan: String = "",
    @SerializedName("items")
    val items: List<OrderItem> = listOf()
)