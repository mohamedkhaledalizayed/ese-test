package com.neqabty.presentation.ui.home

import com.neqabty.presentation.entities.NewsUI

data class HomeViewState(
        var isLoading: Boolean = true,
        var news : List<NewsUI>? = null
)

