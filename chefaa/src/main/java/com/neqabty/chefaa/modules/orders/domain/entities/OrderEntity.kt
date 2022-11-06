package com.neqabty.chefaa.modules.orders.domain.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class OrderEntity(
    val addressId: Int = 0,
    val chefaaOrderNumber: String = "",
    val clientId: String = "",
    val countryCode: String = "",
    val createdAt: String = "",
    val deliveryFees: Int = 0,
    val deliveryNote: String = "",
    val id: Int = 0,
    val orderClient: OrderClientEntity = OrderClientEntity(),
    val orderStatus: OrderStatusEntity = OrderStatusEntity(),
    val phone: String = "",
    val price: Int = 0,
    val priceBeforeDiscount: Int = 0,
    val status: Int = 0,
    val updatedAt: String = "",
    val userPlan: String = "",
    val items: List<ItemEntity>
) : Parcelable