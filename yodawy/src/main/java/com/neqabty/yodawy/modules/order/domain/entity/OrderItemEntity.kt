package com.neqabty.yodawy.modules.order.domain.entity

data class OrderItemEntity(
    val actualWoocommerceProductId: Any,
    val alternativesSKUs: Any,
    val available: Boolean,
    val channel: Any,
    val coveragePercentage: Int,
    val coverageValue: Int,
    val deliveryTimeValue: Int,
    val drug: Any,
    val drugDosageFormId: Int,
    val drugId: String,
    val drugName: String,
    val drugTotal: Int,
    val drugTotalAfterCoverage: Int,
    val maximumQuantityPerUser: Int,
    val price: Int,
    val priceBeforeSale: Int,
    val productType: String,
    val quantity: Int,
    val selectedUnit: Int,
    val variationSKU: Any,
    val wasLimited: Boolean,
    val woocommerceOrderAttributes: Any,
    val woocommerceProductId: Any,
    val yodawyDeal: Boolean
)