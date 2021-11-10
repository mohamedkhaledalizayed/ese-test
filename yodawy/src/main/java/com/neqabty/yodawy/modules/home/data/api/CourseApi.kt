package com.neqabty.yodawy.modules.home.data.api

import com.neqabty.yodawy.modules.home.domain.entity.CourseEntity
import kotlinx.coroutines.flow.Flow

interface CourseApi {
    suspend fun getCourse(): Flow<List<CourseEntity>>
}