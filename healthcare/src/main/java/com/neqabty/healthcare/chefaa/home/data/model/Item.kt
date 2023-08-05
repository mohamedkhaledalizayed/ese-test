package com.neqabty.healthcare.chefaa.home.data.model


import androidx.annotation.Keep

@Keep
data class Item(
    val address_id: String,
    val chefaa_order_number: String,
    val client_id: String,
    val created_at: String,
    val id: Int,
    val note: String?,
    val order_id: Int,
    val product_id: String,
    val product_image: String?,
    val product_name: String?,
    val quantity: String,
    val type: String,
    val updated_at: String
)