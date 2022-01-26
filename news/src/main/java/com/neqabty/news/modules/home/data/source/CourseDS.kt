package com.neqabty.news.modules.home.data.source

import com.neqabty.news.modules.home.data.api.CourseApi
import com.neqabty.news.modules.home.domain.entity.CourseEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CourseDS @Inject constructor(private val courseApi: CourseApi) {
    fun getCourses(): Flow<List<CourseEntity>> {
        return flow {
            emit(courseApi.getCourse())
        }
    }
}