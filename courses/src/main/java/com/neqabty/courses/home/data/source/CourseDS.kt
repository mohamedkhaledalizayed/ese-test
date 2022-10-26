package com.neqabty.courses.home.data.source


import com.neqabty.courses.home.data.api.CourseApi
import com.neqabty.courses.home.data.model.courses.Course
import com.neqabty.courses.home.data.model.reservation.ReservationModel
import okhttp3.MultipartBody
import retrofit2.Response
import javax.inject.Inject

class CourseDS @Inject constructor(private val courseApi: CourseApi) {

    suspend fun getCourses(): List<Course> {
        return courseApi.getCourse().courses
    }

    suspend fun getCourseDetails(id: String): List<Course> {
        return courseApi.getCourseDetails(id).courses
    }

    suspend fun reservations(mobile: String, image: MultipartBody.Part, studentMobile: String, notes: String, offer: String): Response<ReservationModel> {
        return courseApi.reservations(mobile, image, studentMobile, notes, offer)
    }
}