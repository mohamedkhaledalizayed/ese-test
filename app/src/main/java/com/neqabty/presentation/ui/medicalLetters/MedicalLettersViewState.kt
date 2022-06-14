package com.neqabty.presentation.ui.medicalLetters

import com.neqabty.presentation.entities.ClaimingValidationUI
import com.neqabty.presentation.entities.LiteFollowersListUI
import com.neqabty.presentation.entities.MedicalLetterUI
import com.neqabty.presentation.entities.MedicalRenewalUI

data class MedicalLettersViewState(
        var isLoading: Boolean = true,
        var member: ClaimingValidationUI? = null,
        var liteFollowersListUI: List<LiteFollowersListUI>? = null,
        var medicalLetterUI: MedicalLetterUI? = null,
        var pdf: String? = null
)
