package com.neqabty.yodawy.modules.orders.domain.entity


data class OrderEntity(
    val address: OrderAddressEntity,
    val cancellationReasons: List<String>,
    val creationDate: String,
    val currentStatus: String,
    val deliveryFees: Int,
    val id: String,
    val items: List<OrderItemEntity>,
    val orderNumber: String,
    val orderPrice: Double,
    val priceBeforeDiscount: Double,
    val pharmacy: String?,
    val prescriptionImageEntities: List<String>?
)