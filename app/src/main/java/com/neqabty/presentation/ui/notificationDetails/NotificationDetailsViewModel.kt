package com.neqabty.presentation.ui.notificationDetails

import android.arch.lifecycle.MutableLiveData
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

    fun getNotificationDetails(id: String) {
        viewState.value?.notification?.let {
            onNotificationDetailsReceived(it)
        } ?: addDisposable(getNotificationDetails.getNotificationDetails(id)
                .map {
                    it.let {
                        notificationEntityUIMapper.mapFrom(it)
                    }
                }.subscribe(
                        { onNotificationDetailsReceived(it) },
                        {
                            viewState.value = viewState.value?.copy(isLoading = false)
                            errorState.value = it
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
