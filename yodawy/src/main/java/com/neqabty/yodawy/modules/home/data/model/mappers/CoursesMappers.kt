package com.neqabty.yodawy.modules.home.data.model.mappers

import com.neqabty.yodawy.modules.home.data.model.CourseModel
import com.neqabty.yodawy.modules.home.domain.entity.CourseEntity

fun CourseModel.toCourseEntity(): CourseEntity {
    return CourseEntity(this.courseName)
}