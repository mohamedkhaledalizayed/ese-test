package com.neqabty.presentation.ui.notifications

import androidx.lifecycle.MutableLiveData
import com.neqabty.domain.usecases.GetNotifications
import com.neqabty.presentation.common.BaseViewModel
import com.neqabty.presentation.common.SingleLiveEvent
import com.neqabty.presentation.entities.NotificationUI
import com.neqabty.presentation.mappers.NotificationEntityUIMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NotificationsViewModel @Inject constructor(private val getNotifications: GetNotifications) : BaseViewModel() {

    private val notificationEntityUIMapper = NotificationEntityUIMapper()
    var errorState: SingleLiveEvent<Throwable> = SingleLiveEvent()
    var viewState: MutableLiveData<NotificationsViewState> = MutableLiveData()

    init {
        viewState.value = NotificationsViewState()
    }

    fun getNotifications(serviceID: Int, type: Int, userNumber: String) {
        addDisposable(getNotifications.getNotifications(serviceID, type, userNumber)
                .flatMap {
                    it.let {
                        notificationEntityUIMapper.observable(it)
                    }
                }.subscribe(
                        { onNotificationsReceived(it) },
                        {
                            viewState.value = viewState.value?.copy(isLoading = false)
                            errorState.value = handleError(it)
                        }
                )
        )
    }

    private fun onNotificationsReceived(notifications: List<NotificationUI>) {
        val newViewState = viewState.value?.copy(
                isLoading = false,
                notifications = notifications)
        viewState.value = newViewState
    }
}
