package com.neqabty.courses.home.data.api

import com.neqabty.courses.home.data.model.courses.CourseModel
import retrofit2.http.*

interface CourseApi {

    @GET("courses")
    suspend fun getCourse(): CourseModel

    @GET("courses")
    suspend fun getCourseDetails(@Query("filter{id}") id: String): CourseModel

}