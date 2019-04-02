package com.neqabty.presentation.ui.medicalProviders

import com.neqabty.presentation.entities.ProviderUI

data class MedicalProvidersViewState(
        var isLoading: Boolean = true,
        var providers : List<ProviderUI>? = null
)

