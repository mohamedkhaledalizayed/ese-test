package com.neqabty.courses.home.domain.interactors


import com.neqabty.courses.home.data.model.courses.Course
import com.neqabty.courses.home.domain.repository.CoursesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCoursesListUseCase @Inject constructor(private val coursesRepository: CoursesRepository) {
    fun build(): Flow<List<Course>> {
        return coursesRepository.getCoursesList()
    }
}