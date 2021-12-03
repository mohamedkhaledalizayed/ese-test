package com.neqabty.presentation.ui.doctorsReservation

import androidx.lifecycle.MutableLiveData
import com.neqabty.domain.usecases.GetDoctorsReservationData
import com.neqabty.presentation.common.BaseViewModel
import com.neqabty.presentation.common.SingleLiveEvent
import com.neqabty.presentation.mappers.DoctorsReservationEntityUIMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DoctorsReservationViewModel @Inject constructor(
        private val getDoctorsReservationData: GetDoctorsReservationData) : BaseViewModel() {

    private val doctorsReservationEntityUIMapper = DoctorsReservationEntityUIMapper()
    var errorState: SingleLiveEvent<Throwable> = SingleLiveEvent()
    var viewState: MutableLiveData<DoctorsReservationViewState> = MutableLiveData()

    init {
        viewState.value = DoctorsReservationViewState()
    }

    fun getAuthCode(mobileNumber: String) {
        viewState.value?.authCode?.let {
            onAuthCodeReceived(it)
        } ?: addDisposable(getDoctorsReservationData.getData(mobileNumber)
                .map {
                    it.let {
                        doctorsReservationEntityUIMapper.mapFrom(it)
                    }
                }.subscribe(
                        { onAuthCodeReceived(it.authCode) },
                        {
                            viewState.value = viewState.value?.copy(isLoading = false)
                            errorState.value = handleError(it)
                        }
                )
        )
    }

    private fun onAuthCodeReceived(authCode: String) {
        val newViewState = viewState.value?.copy(
            isLoading = false,
            authCode = authCode
        )
        viewState.value = newViewState
    }
}
