package com.neqabty.courses.home.domain.repository


import com.neqabty.courses.home.domain.entity.CourseEntity
import kotlinx.coroutines.flow.Flow

interface CoursesRepository {
    fun getCoursesList(): Flow<List<CourseEntity>>
    fun getCourseDetails(id: String): Flow<List<CourseEntity>>
}