package com.neqabty.courses.offers.domain.interactors



import com.neqabty.courses.offers.data.model.reservation.ReservationModel
import com.neqabty.courses.offers.domain.repository.OffersRepository
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import retrofit2.Response
import javax.inject.Inject

class CourseReservationUseCase @Inject constructor(private val offersRepository: OffersRepository) {

    fun build(mobile: String, image: MultipartBody.Part, studentMobile: String, notes: String, offer: String): Flow<Response<ReservationModel>> {
        return offersRepository.reservations(mobile, image, studentMobile, notes, offer)
    }
}