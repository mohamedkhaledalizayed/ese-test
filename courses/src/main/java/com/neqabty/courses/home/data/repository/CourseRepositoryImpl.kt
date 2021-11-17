package com.neqabty.courses.home.data.repository

import com.neqabty.courses.home.data.model.mappers.toCourseEntity
import com.neqabty.courses.home.data.source.CourseDS
import com.neqabty.courses.home.domain.entity.CourseEntity
import com.neqabty.courses.home.domain.repository.CoursesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CourseRepositoryImpl @Inject constructor(private val courseDS: CourseDS) : CoursesRepository {
    override fun getCoursesList(): Flow<List<CourseEntity>> {
        return courseDS.getCourses().map { it.map { it.toCourseEntity() } }
    }
}