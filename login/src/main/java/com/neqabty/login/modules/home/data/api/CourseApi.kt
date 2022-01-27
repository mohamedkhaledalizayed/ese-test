package com.example.courses.modules.home.data.api

import com.example.courses.modules.home.domain.entity.CourseEntity
import kotlinx.coroutines.flow.Flow

interface CourseApi {
    suspend fun getCourse(): List<CourseEntity>
}