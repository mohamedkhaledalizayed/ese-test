package com.neqabty.news.modules.home.presentation.model.mappers

import com.neqabty.news.modules.home.domain.entity.CourseEntity
import com.neqabty.news.modules.home.presentation.model.CourseUIModel

fun CourseEntity.toCourseUIModel(): CourseUIModel {
    return CourseUIModel(this.courseName)
}