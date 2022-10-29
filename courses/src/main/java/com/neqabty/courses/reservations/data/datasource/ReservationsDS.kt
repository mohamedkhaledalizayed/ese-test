package com.neqabty.courses.reservations.data.datasource


import com.neqabty.courses.reservations.data.api.ReservationsApi
import com.neqabty.courses.reservations.data.model.CourseReservation
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ReservationsDS @Inject constructor(private val reservationsApi: ReservationsApi) {

    fun reservations(phone: String): Flow<List<CourseReservation>>{
        return flow {
            emit(reservationsApi.reservations(phone).courseReservations)
        }
    }

}