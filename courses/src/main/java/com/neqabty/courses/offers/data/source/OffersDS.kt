package com.neqabty.courses.offers.data.source

import com.neqabty.courses.offers.data.api.OffersApi
import com.neqabty.courses.offers.data.model.OfferModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class OffersDS @Inject constructor(private val offersApi: OffersApi) {
    fun getOffers(): Flow<List<OfferModel>>{
        return flow {
            emit(offersApi.getOffers().offerModels)
        }
    }

    fun getCourseOffers(courseId:Int): Flow<List<OfferModel>>{
        return flow {
            emit(offersApi.getCourseOffers("json",courseId).offerModels)
        }
    }
}