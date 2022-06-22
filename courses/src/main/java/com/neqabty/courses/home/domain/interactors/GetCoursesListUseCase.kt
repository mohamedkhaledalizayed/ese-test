package com.neqabty.courses.home.domain.interactors



import com.neqabty.courses.home.domain.entity.CourseEntity
import com.neqabty.courses.home.domain.repository.CoursesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCoursesListUseCase @Inject constructor(private val coursesRepository: CoursesRepository) {
    fun build(): Flow<List<CourseEntity>> {
        return coursesRepository.getCoursesList()
    }
}