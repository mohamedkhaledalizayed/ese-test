package com.neqabty.yodawy.modules.orders.domain.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class OrderEntity(
    val address: OrderAddressEntity,
    val cancellationReasons: List<String>,
    val creationDate: String,
    val currentStatus: String,
    val deliveryFees: Int,
    val id: String,
    val items: List<OrderItemEntity>,
    val notes: String,
    val orderNumber: String,
    val orderPrice: Double,
    val pharmacy: String,
    val prescriptionImageEntities: List<String>?
): Parcelable