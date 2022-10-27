package com.neqabty.courses.home.data.source


import com.neqabty.courses.home.data.api.CourseApi
import com.neqabty.courses.home.data.model.courses.Course
import javax.inject.Inject

class CourseDS @Inject constructor(private val courseApi: CourseApi) {

    suspend fun getCourses(): List<Course> {
        return courseApi.getCourse().courses
    }

    suspend fun getCourseDetails(id: String): List<Course> {
        return courseApi.getCourseDetails(id).courses
    }


}