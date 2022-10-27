package com.neqabty.courses.offers.data.repository

import com.neqabty.courses.offers.data.model.mapper.toOfferEntity
import com.neqabty.courses.offers.data.model.reservation.ReservationModel
import com.neqabty.courses.offers.data.source.OffersDS
import com.neqabty.courses.offers.domain.entity.OfferEntity
import com.neqabty.courses.offers.domain.repository.OffersRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import okhttp3.MultipartBody
import retrofit2.Response
import javax.inject.Inject

class OffersRepositoryImpl @Inject constructor(private val offersDS: OffersDS): OffersRepository {
    override fun getOffers(): Flow<List<OfferEntity>> {
        return offersDS.getOffers().map { it.map { it.toOfferEntity() } }
    }

    override fun getCourseOffers(courseId:Int):Flow<List<OfferEntity>>{
        return offersDS.getCourseOffers(courseId).map { it.map { it.toOfferEntity()} }
    }

    override fun reservations(
        mobile: String,
        image: MultipartBody.Part,
        studentMobile: String,
        notes: String,
        offer: String
    ): Flow<Response<ReservationModel>> {
        return flow {
            emit(offersDS.reservations(mobile, image, studentMobile, notes, offer))
        }
    }
}