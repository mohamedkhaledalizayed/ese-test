package com.example.courses.modules.home.data.model.mappers

import com.example.courses.modules.home.data.model.CourseModel
import com.example.courses.modules.home.domain.entity.CourseEntity

fun CourseModel.toCourseEntity():CourseEntity{
    return CourseEntity(this.courseName)
}