package com.neqabty.healthcare.sustainablehealth.offers.data.source



import com.neqabty.healthcare.sustainablehealth.offers.data.api.OffersApi
import com.neqabty.healthcare.sustainablehealth.offers.data.model.offers.OfferModel
import javax.inject.Inject

class OffersDS @Inject constructor(private val offersApi: OffersApi) {
    suspend fun getOffers(): List<OfferModel> {
        return offersApi.getOffers().data
    }
}