package com.neqabty.presentation.ui.notificationDetails

import androidx.lifecycle.MutableLiveData
import com.neqabty.domain.usecases.GetNotificationDetails
import com.neqabty.presentation.common.BaseViewModel
import com.neqabty.presentation.common.SingleLiveEvent
import com.neqabty.presentation.entities.NotificationUI
import com.neqabty.presentation.mappers.NotificationEntityUIMapper

import javax.inject.Inject

class NotificationDetailsViewModel @Inject constructor(private val getNotificationDetails: GetNotificationDetails) : BaseViewModel() {

    private val notificationEntityUIMapper = NotificationEntityUIMapper()
    var errorState: SingleLiveEvent<Throwable> = SingleLiveEvent()
    var viewState: MutableLiveData<NotificationDetailsViewState> = MutableLiveData()

    init {
        viewState.value = NotificationDetailsViewState()
    }

    fun getNotificationDetails(serviceID: Int, type: Int, userNumber: Int, requestID: Int) {
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
}
