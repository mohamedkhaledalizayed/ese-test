package com.example.courses.modules.home.presentation.model.mappers

import com.example.courses.modules.home.domain.entity.CourseEntity
import com.example.courses.modules.home.presentation.model.CourseUIModel

fun CourseEntity.toCourseUIModel(): CourseUIModel{
    return CourseUIModel(this.courseName)
}