package com.neqabty.courses.reservations.domain.interactors



import com.neqabty.courses.reservations.domain.entity.CourseReservationEntity
import com.neqabty.courses.reservations.domain.repository.ReservationsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetReservationsUseCase @Inject constructor(private val reservationsRepository: ReservationsRepository) {
    fun build(phone: String): Flow<List<CourseReservationEntity>>{
        return reservationsRepository.reservations(phone)
    }
}