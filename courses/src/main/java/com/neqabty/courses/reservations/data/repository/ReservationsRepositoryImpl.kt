package com.neqabty.courses.reservations.data.repository



import com.neqabty.courses.reservations.data.datasource.ReservationsDS
import com.neqabty.courses.reservations.data.model.CourseReservation
import com.neqabty.courses.reservations.domain.entity.CourseReservationEntity
import com.neqabty.courses.reservations.domain.repository.ReservationsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ReservationsRepositoryImpl @Inject constructor(private val reservationsDS: ReservationsDS): ReservationsRepository {

    override fun reservations(phone: String): Flow<List<CourseReservationEntity>> {
        return reservationsDS.reservations(phone).map { it.map { it.toCourseReservationEntity() } }
    }

}

private fun CourseReservation.toCourseReservationEntity(): CourseReservationEntity{
    return CourseReservationEntity(
        contactPhone = contactPhone,
        cost = cost,
        courseName = courseName,
        image = image,
        notes = notes,
        offer = offer,
        paymentStatus = paymentStatus,
        queueNumber = queueNumber,
        status = status,
        student = student
    )
}