package com.neqabty.courses.home.data.repository




import com.neqabty.courses.home.data.model.mappers.toCourseEntity
import com.neqabty.courses.home.data.model.reservation.ReservationModel
import com.neqabty.courses.home.data.source.CourseDS
import com.neqabty.courses.home.domain.entity.CourseEntity
import com.neqabty.courses.home.domain.repository.CoursesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MultipartBody
import retrofit2.Response
import javax.inject.Inject

class CourseRepositoryImpl @Inject constructor(private val courseDS: CourseDS) : CoursesRepository {

    override fun getCoursesList(): Flow<List<CourseEntity>> {
        return flow {
            emit(courseDS.getCourses().map { it.toCourseEntity() })
        }
    }

    override fun getCourseDetails(id: String): Flow<List<CourseEntity>> {
        return flow {
            emit(courseDS.getCourseDetails(id).map { it.toCourseEntity() })
        }
    }

    override fun reservations(
        mobile: String,
        image: MultipartBody.Part,
        studentMobile: String,
        notes: String,
        offer: String
    ): Flow<Response<ReservationModel>> {
        return flow {
            emit(courseDS.reservations(mobile, image, studentMobile, notes, offer))
        }
    }

}

