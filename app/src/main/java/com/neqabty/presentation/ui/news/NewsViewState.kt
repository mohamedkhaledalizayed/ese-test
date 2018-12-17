package com.neqabty.presentation.ui.news

import com.neqabty.presentation.entities.NewsUI

data class NewsViewState(
        var isLoading: Boolean = true,
        var news : List<NewsUI>? = null
)

