package com.neqabty.courses.home.domain.repository

import com.neqabty.courses.home.data.model.courses.Course
import kotlinx.coroutines.flow.Flow

interface CoursesRepository {
    fun getCoursesList(): Flow<List<Course>>
}