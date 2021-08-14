package com.neqabty.presentation.ui.medicalLetters

import com.neqabty.presentation.entities.MedicalLetterUI
import com.neqabty.presentation.entities.MedicalRenewalUI

data class MedicalLettersViewState(
    var isLoading: Boolean = true,
    var medicalRenewalUI: MedicalRenewalUI? = null,
    var medicalLetterUI: MedicalLetterUI? = null
)
