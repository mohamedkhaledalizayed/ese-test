package com.neqabty.yodawy.modules.home.presentation.model.mappers

import com.neqabty.yodawy.modules.home.domain.entity.CourseEntity
import com.neqabty.yodawy.modules.home.presentation.model.CourseUIModel

fun CourseEntity.toCourseUIModel(): CourseUIModel {
    return CourseUIModel(this.courseName)
}