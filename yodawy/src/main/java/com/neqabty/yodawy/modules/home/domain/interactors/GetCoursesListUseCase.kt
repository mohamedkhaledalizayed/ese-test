package com.neqabty.yodawy.modules.home.domain.interactors

import com.neqabty.yodawy.modules.home.domain.entity.CourseEntity
import com.neqabty.yodawy.modules.home.domain.repository.CoursesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCoursesListUseCase @Inject constructor(private val coursesRepository: CoursesRepository) {
    suspend fun build(): Flow<List<CourseEntity>> {
        return coursesRepository.getCoursesList()
    }
}