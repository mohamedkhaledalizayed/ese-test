package com.neqabty.presentation.ui.medicalProviderDetails

import com.neqabty.presentation.entities.ProviderUI

data class MedicalProviderDetailsViewState(
    var isLoading: Boolean = false,
    var isFavorite: Boolean = false,
    var providerDetails: ProviderUI? = null
)
