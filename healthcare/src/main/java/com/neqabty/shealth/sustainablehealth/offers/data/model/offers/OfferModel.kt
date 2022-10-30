package com.neqabty.shealth.sustainablehealth.offers.data.model.offers


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
@Keep
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