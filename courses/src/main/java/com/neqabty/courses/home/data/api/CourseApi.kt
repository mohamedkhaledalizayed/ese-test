package com.neqabty.courses.home.data.api

import com.neqabty.courses.home.data.model.courses.CourseModel
import retrofit2.http.GET

interface CourseApi {
    @GET("courses")
    suspend fun getCourse(): CourseModel
}