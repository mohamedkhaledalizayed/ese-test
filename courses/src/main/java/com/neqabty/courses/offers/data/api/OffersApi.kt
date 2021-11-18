package com.neqabty.courses.offers.data.api

import com.neqabty.courses.offers.data.model.OfferModel
import com.neqabty.courses.offers.data.model.OffersResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface OffersApi {
    @GET("offers")
    suspend fun getOffers(): OffersResponse

    @GET("offers")
    suspend fun getCourseOffers(
        @Query("format") format: String,
        @Query("filter{course.id}") courseId: Int
    ): OffersResponse
}
