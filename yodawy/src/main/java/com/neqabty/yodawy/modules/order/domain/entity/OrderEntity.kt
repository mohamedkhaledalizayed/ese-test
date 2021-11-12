package com.neqabty.yodawy.modules.order.domain.entity


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
    val orderPrice: Int,
    val pharmacy: String,
    val prescriptionImages: Any
)