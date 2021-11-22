package com.neqabty.yodawy.modules.orders.data.model


import com.google.gson.annotations.SerializedName

data class Item(
    @SerializedName("ActualWoocommerceProductId")
    val actualWoocommerceProductId: String?,
    @SerializedName("AlternativesSKUs")
    val alternativesSKUs: String?,
    @SerializedName("Available")
    val available: Boolean,
    @SerializedName("Channel")
    val channel: String?,
    @SerializedName("CoveragePercentage")
    val coveragePercentage: Double,
    @SerializedName("CoverageValue")
    val coverageValue: Double,
    @SerializedName("DeliveryTimeValue")
    val deliveryTimeValue: Int,
    @SerializedName("Drug")
    val drug: String?,
    @SerializedName("DrugDosageFormId")
    val drugDosageFormId: Int,
    @SerializedName("DrugId")
    val drugId: String,
    @SerializedName("DrugName")
    val drugName: String,
    @SerializedName("DrugTotal")
    val drugTotal: Double,
    @SerializedName("DrugTotalAfterCoverage")
    val drugTotalAfterCoverage: Double,
    @SerializedName("MaximumQuantityPerUser")
    val maximumQuantityPerUser: Int,
    @SerializedName("Price")
    val price: Double,
    @SerializedName("PriceBeforeSale")
    val priceBeforeSale: Double,
    @SerializedName("ProductType")
    val productType: String?,
    @SerializedName("Quantity")
    val quantity: Int,
    @SerializedName("SelectedUnit")
    val selectedUnit: Int,
    @SerializedName("VariationSKU")
    val variationSKU: String?,
    @SerializedName("WasLimited")
    val wasLimited: Boolean,
    @SerializedName("WoocommerceOrderAttributes")
    val woocommerceOrderAttributes: List<Any>?,
    @SerializedName("WoocommerceProductId")
    val woocommerceProductId: String?,
    @SerializedName("YodawyDeal")
    val yodawyDeal: Boolean
)