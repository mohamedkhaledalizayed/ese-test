package com.neqabty.yodawy.modules.products.data.model


import com.google.gson.annotations.SerializedName

data class ProductModel(
    @SerializedName("active")
    val active: Boolean,
    @SerializedName("id")
    val id: String,
    @SerializedName("image")
    val image: String?,
    @SerializedName("isLimitedAvailability")
    val isLimitedAvailability: Boolean,
    @SerializedName("isMedication")
    val isMedication: Boolean,
    @SerializedName("name")
    val name: String,
    @SerializedName("outOfStock")
    val outOfStock: Boolean,
    @SerializedName("regularPrice")
    val regularPrice: Float,
    @SerializedName("salePrice")
    val salePrice: Float
)