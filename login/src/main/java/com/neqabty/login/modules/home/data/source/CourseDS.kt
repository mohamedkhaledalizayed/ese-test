package com.example.courses.modules.home.data.source

import com.example.courses.modules.home.data.api.CourseApi
import com.example.courses.modules.home.data.model.CourseModel
import com.example.courses.modules.home.domain.entity.CourseEntity
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