package com.neqabty.courses.offers.data.api

import com.neqabty.courses.offers.data.model.OffersResponse
import com.neqabty.courses.offers.data.model.reservation.ReservationModel
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface OffersApi {
    @GET("offers")
    suspend fun getOffers(): OffersResponse

    @GET("offers")
    suspend fun getCourseOffers(
        @Query("format") format: String,
        @Query("filter{course.id}") courseId: Int
    ): OffersResponse

    @Multipart
    @POST("reservations")
    suspend fun reservations(
        @Part("contact_phone") mobile: String,
        @Part image: MultipartBody.Part,
        @Part("student_mobile") student_mobile: String,
        @Part("notes") notes: String,
        @Part("offer") offer: String): Response<ReservationModel>
}
