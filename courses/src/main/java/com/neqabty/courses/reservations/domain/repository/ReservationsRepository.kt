package com.neqabty.courses.reservations.domain.repository




import com.neqabty.courses.reservations.domain.entity.CourseReservationEntity
import kotlinx.coroutines.flow.Flow



interface ReservationsRepository {
    fun reservations(phone: String): Flow<List<CourseReservationEntity>>
}