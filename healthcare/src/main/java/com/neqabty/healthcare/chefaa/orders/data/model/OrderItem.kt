package com.neqabty.healthcare.chefaa.orders.data.model


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class OrderItem(
    @SerializedName("address_id")
    val addressId: String = "",
    @SerializedName("chefaa_order_number")
    val chefaaOrderNumber: String = "",
    @SerializedName("client_id")
    val clientId: String = "",
    @SerializedName("created_at")
    val createdAt: String = "",
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("note")
    val note: String? = null,
    @SerializedName("order_id")
    val orderId: Int = 0,
    @SerializedName("product_id")
    val productId: String = "",
    @SerializedName("product_image")
    val productImage: String? = null,
    @SerializedName("product_name")
    val productName: String? = null,
    @SerializedName("quantity")
    val quantity: String = "",
    @SerializedName("type")
    val type: String = "",
    @SerializedName("updated_at")
    val updatedAt: String = ""
)