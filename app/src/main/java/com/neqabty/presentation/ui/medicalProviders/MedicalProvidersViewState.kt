package com.neqabty.presentation.ui.medicalProviders

import com.neqabty.presentation.entities.MedicalDirectoryLookupsUI
import com.neqabty.presentation.entities.MedicalDirectoryProviderUI

data class MedicalProvidersViewState(
        var isLoading: Boolean = true,
        var specializations: List<MedicalDirectoryLookupsUI.DoctorSpecialization>? = null,
        var providers: List<MedicalDirectoryProviderUI>? = null
)
