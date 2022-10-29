package com.neqabty.courses.offers.domain.repository


import com.neqabty.courses.offers.data.model.RescheduleRequestBody
import com.neqabty.courses.offers.data.model.reservation.ReservationModel
import com.neqabty.courses.offers.domain.entity.OfferEntity
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import retrofit2.Response

interface OffersRepository {
    fun getOffers(): Flow<List<OfferEntity>>
    fun getCourseOffers(courseId: Int): Flow<List<OfferEntity>>
    fun reservations(mobile: String, image: MultipartBody.Part, studentMobile: String, notes: String, offer: String): Flow<Response<ReservationModel>>
    fun rescheduleRequests(rescheduleRequestBody: RescheduleRequestBody): Flow<String>
}