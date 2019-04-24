package com.neqabty.presentation.ui.notifications

import com.neqabty.presentation.entities.NotificationUI

data class NotificationsViewState(
    var isLoading: Boolean = true,
    var notifications: List<NotificationUI>? = null
)
