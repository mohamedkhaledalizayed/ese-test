package com.neqabty.courses.offers.data.api

import com.neqabty.courses.offers.data.model.OfferModel
import retrofit2.http.GET

interface OffersApi {
    @GET("offers")
    suspend fun getOffers(): List<OfferModel>
}