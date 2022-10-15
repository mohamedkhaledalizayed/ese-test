package com.neqabty.presentation.ui.notificationDetails

import androidx.lifecycle.MutableLiveData
import com.neqabty.domain.usecases.GetMedicalLetterByID
import com.neqabty.domain.usecases.GetNotificationDetails
import com.neqabty.presentation.common.BaseViewModel
import com.neqabty.presentation.common.SingleLiveEvent
import com.neqabty.presentation.entities.NotificationUI
import com.neqabty.presentation.mappers.MedicalLetterItemEntityUIMapper
import com.neqabty.presentation.mappers.NotificationEntityUIMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NotificationDetailsViewModel @Inject constructor(private val getNotificationDetails: GetNotificationDetails,
private val getMedicalLetterByID: GetMedicalLetterByID
) : BaseViewModel() {

    private val notificationEntityUIMapper = NotificationEntityUIMapper()
    private val medicalLetterItemEntityUIMapper = MedicalLetterItemEntityUIMapper()
    var errorState: SingleLiveEvent<Throwable> = SingleLiveEvent()
    var viewState: MutableLiveData<NotificationDetailsViewState> = MutableLiveData()

    init {
        viewState.value = NotificationDetailsViewState()
    }

    fun getNotificationDetails(serviceID: Int, type: Int, userNumber: String, requestID: String) {
        viewState.value?.notification?.let {
            onNotificationDetailsReceived(it)
        } ?: addDisposable(getNotificationDetails.getNotificationDetails(serviceID, type, userNumber, requestID)
                .map {
                    it.let {
                        notificationEntityUIMapper.mapFrom(it)
                    }
                }.subscribe(
                        { onNotificationDetailsReceived(it) },
                        {
                            viewState.value = viewState.value?.copy(isLoading = false)
                            errorState.value = handleError(it)
                        }
                )
        )
    }

    private fun onNotificationDetailsReceived(notification: NotificationUI) {
        val newViewState = viewState.value?.copy(
                isLoading = false,
                notification = notification)
        viewState.value = newViewState
    }

    fun getAttachment(mobileNumber: String, userNumber: String, id: String) {
        viewState.value = viewState.value?.copy(isLoading = true)
        addDisposable(getMedicalLetterByID.getMedicalLetterByID(mobileNumber, userNumber, id)
            .flatMap {
                it.let {
                    medicalLetterItemEntityUIMapper.observable(it)
                }
            }.subscribe(
                {
                    viewState.value = viewState.value?.copy(isLoading = false, pdf = it.report)
                },
                {
                    viewState.value = viewState.value?.copy(isLoading = false)
                    errorState.value = handleError(it)
                }
            )
        )
    }
}
