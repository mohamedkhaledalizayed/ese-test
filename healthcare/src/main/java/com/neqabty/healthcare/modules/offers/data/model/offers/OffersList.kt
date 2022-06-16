package com.neqabty.healthcare.modules.offers.data.model.offers


import com.google.gson.annotations.SerializedName

data class OffersList(
    @SerializedName("data")
    val data: List<OfferModel>,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean
)