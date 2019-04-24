package com.neqabty.presentation.ui.medicalProfessions

import com.neqabty.presentation.entities.SpecializationUI

data class MedicalProfessionsViewState(
    var isLoading: Boolean = true,
    var professions: List<SpecializationUI>? = null
)
