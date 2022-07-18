package com.neqabty.healthcare.modules.offers.data.model.offers


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
@Keep
data class OffersList(
    @SerializedName("data")
    val data: List<OfferModel>,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean
)