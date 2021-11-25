package com.neqabty.yodawy.modules.orders.data.model.response.order


import com.google.gson.annotations.SerializedName

data class Item(
    @SerializedName("ActualWoocommerceProductId")
    val actualWoocommerceProductId: Any,
    @SerializedName("AlternativesSKUs")
    val alternativesSKUs: Any,
    @SerializedName("Available")
    val available: Boolean,
    @SerializedName("Channel")
    val channel: Any,
    @SerializedName("CoveragePercentage")
    val coveragePercentage: Int,
    @SerializedName("CoverageValue")
    val coverageValue: Int,
    @SerializedName("DeliveryTimeValue")
    val deliveryTimeValue: Int,
    @SerializedName("Drug")
    val drug: Any,
    @SerializedName("DrugDosageFormId")
    val drugDosageFormId: Int,
    @SerializedName("DrugId")
    val drugId: String,
    @SerializedName("DrugName")
    val drugName: String,
    @SerializedName("DrugTotal")
    val drugTotal: Int,
    @SerializedName("DrugTotalAfterCoverage")
    val drugTotalAfterCoverage: Int,
    @SerializedName("MaximumQuantityPerUser")
    val maximumQuantityPerUser: Int,
    @SerializedName("Price")
    val price: Int,
    @SerializedName("PriceBeforeSale")
    val priceBeforeSale: Int,
    @SerializedName("ProductType")
    val productType: String,
    @SerializedName("Quantity")
    val quantity: Int,
    @SerializedName("SelectedUnit")
    val selectedUnit: Int,
    @SerializedName("VariationSKU")
    val variationSKU: Any,
    @SerializedName("WasLimited")
    val wasLimited: Boolean,
    @SerializedName("WoocommerceOrderAttributes")
    val woocommerceOrderAttributes: Any,
    @SerializedName("WoocommerceProductId")
    val woocommerceProductId: Int,
    @SerializedName("YodawyDeal")
    val yodawyDeal: Boolean
)