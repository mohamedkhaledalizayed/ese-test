package com.neqabty.yodawy.modules.home.data.repository

import com.neqabty.yodawy.modules.home.data.source.CourseDS
import com.neqabty.yodawy.modules.home.domain.entity.CourseEntity
import com.neqabty.yodawy.modules.home.domain.repository.CoursesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CourseRepositoryImpl @Inject constructor(private val courseDS: CourseDS): CoursesRepository {
    override suspend fun getCoursesList(): Flow<List<CourseEntity>> {
        return courseDS.getCourses()
    }
}