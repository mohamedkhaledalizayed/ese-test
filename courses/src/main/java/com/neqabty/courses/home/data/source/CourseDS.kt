package com.neqabty.courses.home.data.source

import com.neqabty.courses.home.data.api.CourseApi
import com.neqabty.courses.home.domain.entity.CourseEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CourseDS @Inject constructor(private val courseApi: CourseApi) {
    suspend fun getCourses(): Flow<List<CourseEntity>> {
        return courseApi.getCourse()
    }
}