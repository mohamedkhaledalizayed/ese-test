package com.neqabty.courses.home.domain.repository

import com.neqabty.courses.home.domain.entity.CourseEntity
import kotlinx.coroutines.flow.Flow

interface CoursesRepository {
    suspend fun getCoursesList(): Flow<List<CourseEntity>>
}