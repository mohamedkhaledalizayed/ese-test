package com.neqabty.trips.modules.home.data.api

import com.neqabty.trips.modules.home.data.model.CityResponse
import retrofit2.http.GET

interface CitiesApi {
    @GET("cities")
    suspend fun getCourse(): CityResponse
}