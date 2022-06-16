package com.neqabty.healthcare.modules.offers.data.model.offers


import com.google.gson.annotations.SerializedName

data class OfferModel(
    @SerializedName("description")
    val description: String,
    @SerializedName("expiry_date")
    val expiryDate: String,
    @SerializedName("percentage")
    val percentage: String,
    @SerializedName("title")
    val title: String
)