package com.neqabty.courses.home.data.api

import com.neqabty.courses.home.data.model.CourseModel
import com.neqabty.courses.home.domain.entity.CourseEntity
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET

interface CourseApi {
    @GET("courses")
    suspend fun getCourse(): List<CourseModel>
}