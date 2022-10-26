package com.neqabty.courses.home.data.api

import com.neqabty.courses.home.data.model.courses.CourseModel
import com.neqabty.courses.home.data.model.reservation.ReservationModel
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface CourseApi {

    @GET("courses")
    suspend fun getCourse(): CourseModel

    @GET("courses")
    suspend fun getCourseDetails(@Query("filter{id}") id: String): CourseModel

    @Multipart
    @POST("reservations")
    suspend fun reservations(
        @Part("contact_phone") mobile: String,
        @Part image: MultipartBody.Part,
        @Part("student_mobile") student_mobile: String,
        @Part("notes") notes: String,
        @Part("offer") offer: String): Response<ReservationModel>
}