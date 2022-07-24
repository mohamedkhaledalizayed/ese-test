package com.neqabty.courses.offers.domain.entity

data class CourseInOfferEntity(
    val id: Int = 0,
    val image: String = "",
    val labEntity: LabEntity = LabEntity(),
    val numOfSessions: Int = 0,
    val prerequisites: String = "",
    val syllabus: String = "",
    val title: String = ""
)