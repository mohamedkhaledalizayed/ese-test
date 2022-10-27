package com.neqabty.courses.offers.presentation.viewmodel


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neqabty.courses.core.utils.AppUtils
import com.neqabty.courses.core.utils.Resource
import com.neqabty.courses.offers.data.model.reservation.ReservationModel
import com.neqabty.courses.offers.domain.entity.OfferEntity
import com.neqabty.courses.offers.domain.interactors.CourseReservationUseCase
import com.neqabty.courses.offers.domain.interactors.GetCourseOffersUseCase
import com.neqabty.courses.offers.domain.interactors.GetOffersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class OffersViewModel @Inject constructor(
    private val getOffersUseCase: GetOffersUseCase,
    private val getCourseOffersUseCase: GetCourseOffersUseCase,
    private val courseReservationUseCase: CourseReservationUseCase
) : ViewModel() {
    val offers = MutableLiveData<Resource<List<OfferEntity>>>()
    fun getCoursesOffers(courseId: Int) {
        offers.postValue(Resource.loading(data = null))
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if(courseId != -1)
                    getCourseOffersUseCase.build(courseId).collect {
                        offers.postValue(Resource.success(data = it))
                    }
                else
                    getOffersUseCase.build().collect {
                        offers.postValue(Resource.success(data = it))
                    }
            }catch (e:Throwable){
                offers.postValue(Resource.error(data = null, message = AppUtils().handleError(e)))
            }
        }
    }

    val reservationStatus = MutableLiveData<Resource<ReservationModel>>()
    fun reservations(mobile: String, image: MultipartBody.Part, studentMobile: String, notes: String, offer: String) {
        reservationStatus.postValue(Resource.loading(data = null))
        viewModelScope.launch(Dispatchers.IO) {
            try {
                courseReservationUseCase.build(mobile, image, studentMobile, notes, offer).collect {
                    if (it.isSuccessful){
                        reservationStatus.postValue(Resource.success(data = it.body()!!))
                    }else{
                        val jObjError = JSONObject(it.errorBody()!!.string()).toString()
                        reservationStatus.postValue(Resource.error(data = null, message = jObjError))
                    }
                }
            } catch (e: Throwable) {
                reservationStatus.postValue(Resource.error(data = null, message = AppUtils().handleError(e)))
            }
        }
    }
}