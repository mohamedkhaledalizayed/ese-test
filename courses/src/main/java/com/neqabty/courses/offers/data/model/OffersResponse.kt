package com.neqabty.courses.offers.data.model

import com.google.gson.annotations.SerializedName

data class OffersResponse(
    @SerializedName("course_offers")
    val offers: List<OfferModel>
)