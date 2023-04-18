package com.neqabty.healthcare.chefaa.orders.domain.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class OrderEntity(
    val addressId: Int = 0,
    val chefaaOrderNumber: String = "",
    val clientId: String = "",
    val countryCode: String = "",
    val createdAt: String = "",
    val deliveryFees: Float = 0f,
    val deliveryNote: String = "",
    val id: Int = 0,
    val orderStatus: OrderStatusEntity = OrderStatusEntity(),
    val phone: String = "",
    val price: Float = 0f,
    val priceBeforeDiscount: Float = 0f,
    val status: Int = 0,
    val updatedAt: String = "",
    val userPlan: String = "",
    val items: List<ItemEntity>
) : Parcelable