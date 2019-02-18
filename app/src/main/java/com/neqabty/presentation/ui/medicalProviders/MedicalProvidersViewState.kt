package com.neqabty.presentation.ui.medicalProviders

import com.neqabty.presentation.entities.MedicalProviderUI

data class MedicalProvidersViewState(
        var isLoading: Boolean = true,
        var providers : List<MedicalProviderUI>? = null
)

