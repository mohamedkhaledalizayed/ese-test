package com.neqabty.courses.home.domain.entity

data class CourseEntity(
    val id: Int,
    val image: Any,
    val numOfSessions: Int,
    val prerequisites: Any,
    val syllabus: String,
    val title: String
)
