package com.neqabty.yodawy.modules.orders.domain.entity


data class OrderItemEntity(
    val actualWoocommerceProductId: String?,
    val alternativesSKUs: String?,
    val available: Boolean,
    val channel: String?,
    val coveragePercentage: Double?,
    val coverageValue: Double?,
    val deliveryTimeValue: Int?,
    val drug: String?,
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
    val variationSKU: String?,
    val wasLimited: Boolean,
    val removed: Boolean,
    val woocommerceProductId: String?,
    val yodawyDeal: Boolean
)