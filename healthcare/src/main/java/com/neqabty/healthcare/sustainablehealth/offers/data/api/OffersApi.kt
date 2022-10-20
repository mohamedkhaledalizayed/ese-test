package com.neqabty.healthcare.sustainablehealth.offers.data.api


import com.neqabty.healthcare.sustainablehealth.offers.data.model.offers.OffersList
import retrofit2.http.GET

interface OffersApi {
    @GET("offers")
    suspend fun getOffers(): OffersList
}