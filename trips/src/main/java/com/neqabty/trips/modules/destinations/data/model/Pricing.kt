package com.neqabty.trips.modules.destinations.data.model


import com.google.gson.annotations.SerializedName

data class Pricing(
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("period")
    val period: Period = Period(),
    @SerializedName("period_price")
    val periodPrice: Double = 0.0,
    @SerializedName("price_per")
    val pricePer: String = "",
    @SerializedName("unit_features_class")
    val unitFeaturesClass: Int = 0
)