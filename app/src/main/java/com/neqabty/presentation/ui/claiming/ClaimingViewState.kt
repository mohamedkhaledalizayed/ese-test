package com.neqabty.presentation.ui.claiming

import com.neqabty.presentation.entities.AreaUI
import com.neqabty.presentation.entities.DoctorUI
import com.neqabty.presentation.entities.SpecializationUI

data class ClaimingViewState(
        var isLoading: Boolean = true,
        var doctors : List<DoctorUI>? = null,
        var areas : List<AreaUI>? = null,
        var specializations : List<SpecializationUI>? = null
)
