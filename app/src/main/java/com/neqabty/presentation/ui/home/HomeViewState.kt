package com.neqabty.presentation.ui.home

import com.neqabty.presentation.entities.NewsUI
import com.neqabty.presentation.entities.NotificationUI
import com.neqabty.presentation.entities.TripUI

data class HomeViewState(
    var isLoading: Boolean = true,
    var news: List<NewsUI>? = null,
    var trips: List<TripUI>? = null,
    var appVersion: Int? = null,
    var notificationsCount: Int? = null
)
