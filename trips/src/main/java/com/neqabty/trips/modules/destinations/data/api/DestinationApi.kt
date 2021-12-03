package com.neqabty.trips.modules.destinations.data.api

import com.neqabty.trips.modules.destinations.data.model.DestinationResponse
import retrofit2.http.GET

interface DestinationApi {
    @GET("destinations")
    suspend fun getDestinations(): DestinationResponse
}