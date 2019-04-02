package com.neqabty.presentation.ui.medicalProviderDetails

import com.neqabty.presentation.entities.ProviderUI

data class MedicalProviderDetailsViewState(
        var isLoading: Boolean = true,
        var isFavorite: Boolean = false,
        var providerDetails : ProviderUI? = null
)

