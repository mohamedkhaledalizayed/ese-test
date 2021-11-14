package com.neqabty.courses.home.data.api

import com.neqabty.courses.home.domain.entity.CourseEntity
import kotlinx.coroutines.flow.Flow

interface CourseApi {
    suspend fun getCourse(): Flow<List<CourseEntity>>
}