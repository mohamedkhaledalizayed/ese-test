package com.neqabty.news.modules.home.data.api

import com.neqabty.news.modules.home.domain.entity.CourseEntity

interface CourseApi {
    suspend fun getCourse(): List<CourseEntity>
}