package com.neqabty.news.modules.home.data.model.mappers

import com.neqabty.news.modules.home.data.model.CourseModel
import com.neqabty.news.modules.home.domain.entity.CourseEntity

fun CourseModel.toCourseEntity(): CourseEntity {
    return CourseEntity(this.courseName)
}