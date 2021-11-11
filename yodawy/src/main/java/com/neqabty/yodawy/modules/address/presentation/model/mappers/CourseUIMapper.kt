package com.neqabty.yodawy.modules.address.presentation.model.mappers

import com.neqabty.yodawy.modules.address.domain.entity.CourseEntity
import com.neqabty.yodawy.modules.address.presentation.model.CourseUIModel

fun CourseEntity.toCourseUIModel(): CourseUIModel {
    return CourseUIModel(this.courseName)
}