package com.neqabty.yodawy.modules.orders.domain.entity

data class OrderItemEntity(
    val actualWoocommerceProductId: Any?,
    val alternativesSKUs: Any?,
    val available: Boolean,
    val channel: Any?,
    val coveragePercentage: Double?,
    val coverageValue: Double?,
    val deliveryTimeValue: Int?,
    val drug: Any?,
    val drugDosageFormId: Int?,
    val drugId: String?,
    val drugName: String?,
    val drugTotal: Double,
    val drugTotalAfterCoverage: Double?,
    val maximumQuantityPerUser: Int?,
    val price: Double?,
    val priceBeforeSale: Double?,
    val productType: String?,
    val quantity: Int?,
    val selectedUnit: Int?,
    val variationSKU: Any?,
    val wasLimited: Boolean,
    val woocommerceOrderAttributes: Any?,
    val woocommerceProductId: Any?,
    val yodawyDeal: Boolean
)