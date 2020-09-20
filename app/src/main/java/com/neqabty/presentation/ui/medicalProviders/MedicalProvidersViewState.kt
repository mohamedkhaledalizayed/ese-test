package com.neqabty.presentation.ui.medicalProviders

import com.neqabty.presentation.entities.ProviderUI
import com.neqabty.presentation.entities.SpecializationUI

data class MedicalProvidersViewState(
        var isLoading: Boolean = true,
        var professions: List<SpecializationUI>? = null,
        var providers: List<ProviderUI>? = null
)
