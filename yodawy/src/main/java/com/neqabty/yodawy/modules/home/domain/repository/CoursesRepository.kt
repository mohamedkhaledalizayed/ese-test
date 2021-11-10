package com.neqabty.yodawy.modules.home.domain.repository

import com.neqabty.yodawy.modules.home.domain.entity.CourseEntity
import kotlinx.coroutines.flow.Flow

interface CoursesRepository {
    suspend fun getCoursesList(): Flow<List<CourseEntity>>
}