package com.neqabty.courses.reservations.data.api

import com.neqabty.courses.reservations.data.model.ReservationsModel
import retrofit2.http.GET
import retrofit2.http.Query

interface ReservationsApi {

    @GET("reservations")
    suspend fun reservations(@Query("filter{contact_phone}") phone: String): ReservationsModel

}