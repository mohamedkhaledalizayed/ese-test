package com.neqabty.courses.home.domain.repository


import com.neqabty.courses.home.data.model.reservation.ReservationModel
import com.neqabty.courses.home.domain.entity.CourseEntity
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import retrofit2.Response

interface CoursesRepository {
    fun getCoursesList(): Flow<List<CourseEntity>>
    fun getCourseDetails(id: String): Flow<List<CourseEntity>>
    fun reservations(mobile: String, image: MultipartBody.Part, studentMobile: String, notes: String, offer: String): Flow<Response<ReservationModel>>
}