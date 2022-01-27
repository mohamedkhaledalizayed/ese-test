package com.example.courses.modules.home.data.repository

import com.example.courses.modules.home.data.model.CourseModel
import com.example.courses.modules.home.data.model.mappers.toCourseEntity
import com.example.courses.modules.home.data.source.CourseDS
import com.example.courses.modules.home.domain.entity.CourseEntity
import com.example.courses.modules.home.domain.repository.CoursesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CourseRepositoryImpl @Inject constructor(private val courseDS: CourseDS): CoursesRepository {
    override fun getCoursesList(): Flow<List<CourseEntity>> {
        return courseDS.getCourses()
    }
}