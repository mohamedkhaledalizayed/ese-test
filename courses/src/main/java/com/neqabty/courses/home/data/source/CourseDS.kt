package com.neqabty.courses.home.data.source

import com.neqabty.courses.home.data.api.CourseApi
import com.neqabty.courses.home.data.model.CourseModel
import com.neqabty.courses.home.domain.entity.CourseEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CourseDS @Inject constructor(private val courseApi: CourseApi) {
    fun getCourses(): Flow<List<CourseModel>> {
        return flow {
            emit(courseApi.getCourse())
        }
    }
}