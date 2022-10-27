package com.neqabty.courses.offers.data.source

import com.neqabty.courses.offers.data.api.OffersApi
import com.neqabty.courses.offers.data.model.OfferModel
import com.neqabty.courses.offers.data.model.reservation.ReservationModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MultipartBody
import retrofit2.Response
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

    suspend fun reservations(mobile: String, image: MultipartBody.Part, studentMobile: String, notes: String, offer: String): Response<ReservationModel> {
        return offersApi.reservations(mobile, image, studentMobile, notes, offer)
    }
}