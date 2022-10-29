package com.neqabty.courses.reservations.presentation.view


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neqabty.courses.core.utils.AppUtils
import com.neqabty.courses.core.utils.Resource
import com.neqabty.courses.reservations.domain.entity.CourseReservationEntity
import com.neqabty.courses.reservations.domain.interactors.GetReservationsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReservationsViewModel @Inject constructor(
    private val reservationsUseCase: GetReservationsUseCase
) : ViewModel() {

    val reservations = MutableLiveData<Resource<List<CourseReservationEntity>>>()
    fun getReservations(phone: String) {
        reservations.postValue(Resource.loading(data = null))
        viewModelScope.launch(Dispatchers.IO) {
            try {
                reservationsUseCase.build(phone).collect {
                    reservations.postValue(Resource.success(data = it))
                }

            } catch (e: Throwable) {
                reservations.postValue(
                    Resource.error(
                        data = null,
                        message = AppUtils().handleError(e)
                    )
                )
            }
        }
    }

}