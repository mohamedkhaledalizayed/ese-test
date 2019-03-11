package com.neqabty.presentation.ui.notifications

import android.arch.lifecycle.MutableLiveData
import com.neqabty.domain.usecases.GetNotifications
import com.neqabty.presentation.common.BaseViewModel
import com.neqabty.presentation.common.SingleLiveEvent
import com.neqabty.presentation.entities.NotificationUI
import com.neqabty.presentation.mappers.NotificationEntityUIMapper
import com.neqabty.testing.OpenForTesting
import javax.inject.Inject


@OpenForTesting
class NotificationsViewModel @Inject constructor(private val getNotifications: GetNotifications) : BaseViewModel() {

    private val notificationEntityUIMapper = NotificationEntityUIMapper()
    var errorState: SingleLiveEvent<Throwable> = SingleLiveEvent()
    var viewState: MutableLiveData<NotificationsViewState> = MutableLiveData()

    init {
        viewState.value = NotificationsViewState()
    }

    fun getNotifications(userNumber : String,subSyndicateId : String) {
        viewState.value?.notifications?.let {
            onNotificationsReceived(it)
        } ?: addDisposable(getNotifications.getNotifications(userNumber,subSyndicateId)
                .flatMap {
                    it.let {
                        notificationEntityUIMapper.observable(it)
                    }
                }.subscribe(
                        { onNotificationsReceived(it) },
                        {
                            viewState.value = viewState.value?.copy(isLoading = false)
                            errorState.value = it
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
