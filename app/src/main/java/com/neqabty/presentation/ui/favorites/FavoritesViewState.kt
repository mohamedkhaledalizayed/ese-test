package com.neqabty.presentation.ui.favorites

import com.neqabty.presentation.entities.ProviderUI

data class FavoritesViewState(
        var isLoading: Boolean = true,
        var favorites : List<ProviderUI>? = null
)

