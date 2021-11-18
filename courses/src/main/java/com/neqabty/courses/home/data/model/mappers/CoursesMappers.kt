package com.neqabty.courses.home.data.model.mappers

import com.neqabty.courses.home.data.model.CourseModel
import com.neqabty.courses.home.domain.entity.CourseEntity

fun CourseModel.toCourseEntity(): CourseEntity {
    return CourseEntity(id, image ?:"", numOfSessions, prerequisites, syllabus, title)
}