package com.neqabty.presentation.ui.notificationDetails

import com.neqabty.presentation.entities.NotificationUI

data class NotificationDetailsViewState(
        var isLoading: Boolean = true,
        var notification : NotificationUI? = null
)
