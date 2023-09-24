package com.neqabty.healthcare.pharmacymart.orders.domain.entity.orderdetails



data class OrderItemEntity(
    val actualDeliveryDatetime: String,
    val attachments: List<String>,
    val cancellationReason: String,
    val cancelledBy: String,
    val createdAt: String,
    val deliveryFees: String,
    val deliveryMobile: String,
    val deliveryNote: String,
    val id: Int,
    val importedDiscountPercentage: String,
    val items: List<ItemsEntity>,
    val localDiscountPercentage: String,
    val mobile: String,
    val orderNumber: String,
    val orderStatusTitle: String,
    val orderStatusId: Int,
    val orderText: String,
    val paid: Int,
    val priceAfterDiscount: String,
    val priceBeforeDiscount: String,
    val status: Int,
    val totalPrice: String,
)