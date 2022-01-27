package com.example.courses.modules.home.domain.repository

import com.example.courses.modules.home.data.model.CourseModel
import com.example.courses.modules.home.domain.entity.CourseEntity
import kotlinx.coroutines.flow.Flow

interface CoursesRepository {
    fun getCoursesList(): Flow<List<CourseEntity>>
}