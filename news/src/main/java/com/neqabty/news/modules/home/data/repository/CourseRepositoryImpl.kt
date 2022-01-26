package com.neqabty.news.modules.home.data.repository

import com.neqabty.news.modules.home.data.source.CourseDS
import com.neqabty.news.modules.home.domain.entity.CourseEntity
import com.neqabty.news.modules.home.domain.repository.CoursesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CourseRepositoryImpl @Inject constructor(private val courseDS: CourseDS): CoursesRepository {
    override fun getCoursesList(): Flow<List<CourseEntity>> {
        return courseDS.getCourses()
    }
}