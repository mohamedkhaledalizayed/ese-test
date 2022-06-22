package com.neqabty.courses.home.data.model.mappers

import com.neqabty.courses.home.data.model.courses.Course
import com.neqabty.courses.home.domain.entity.CourseEntity

fun Course.toCourseEntity(): CourseEntity {
    return CourseEntity(
        id, image ?: "", numOfSessions ?: 0,
        prerequisites, syllabus ?: "", title ?: ""
    )
}