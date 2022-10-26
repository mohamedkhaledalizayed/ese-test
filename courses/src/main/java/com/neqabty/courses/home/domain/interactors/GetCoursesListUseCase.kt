package com.neqabty.courses.home.domain.interactors



import com.neqabty.courses.home.data.model.reservation.ReservationModel
import com.neqabty.courses.home.domain.entity.CourseEntity
import com.neqabty.courses.home.domain.repository.CoursesRepository
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import retrofit2.Response
import javax.inject.Inject

class GetCoursesListUseCase @Inject constructor(private val coursesRepository: CoursesRepository) {

    fun build(): Flow<List<CourseEntity>> {
        return coursesRepository.getCoursesList()
    }

    fun build(id: String): Flow<List<CourseEntity>> {
        return coursesRepository.getCourseDetails(id)
    }

    fun build(mobile: String, image: MultipartBody.Part, studentMobile: String, notes: String, offer: String): Flow<Response<ReservationModel>> {
        return coursesRepository.reservations(mobile, image, studentMobile, notes, offer)
    }
}