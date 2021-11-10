package com.neqabty.yodawy.modules.home.data.source

import com.neqabty.yodawy.modules.home.data.api.CourseApi
import com.neqabty.yodawy.modules.home.domain.entity.CourseEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CourseDS @Inject constructor(private val courseApi: CourseApi) {
    suspend fun getCourses(): Flow<List<CourseEntity>> {
        return courseApi.getCourse()
    }
}