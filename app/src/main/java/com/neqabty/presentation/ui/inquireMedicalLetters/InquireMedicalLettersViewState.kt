package com.neqabty.presentation.ui.inquireMedicalLetters

import com.neqabty.presentation.entities.MedicalLetterUI

data class InquireMedicalLettersViewState(
    var isLoading: Boolean = false,
    var medicalLetterItemUI: MedicalLetterUI.LetterItem? = null
)
