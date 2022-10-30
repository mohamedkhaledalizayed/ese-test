package com.neqabty.shealth.sustainablehealth.offers.data.api


import com.neqabty.shealth.sustainablehealth.offers.data.model.offers.OffersList
import retrofit2.http.GET

interface OffersApi {
    @GET("offers")
    suspend fun getOffers(): OffersList
}