package com.neqabty.news.modules.home.domain.repository

import com.neqabty.news.modules.home.domain.entity.CourseEntity
import kotlinx.coroutines.flow.Flow

interface CoursesRepository {
    fun getCoursesList(): Flow<List<CourseEntity>>
}