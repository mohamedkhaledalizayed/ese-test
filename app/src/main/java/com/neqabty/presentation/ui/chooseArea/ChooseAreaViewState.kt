package com.neqabty.presentation.ui.chooseArea

import com.neqabty.presentation.entities.MedicalDirectoryLookupsUI

data class ChooseAreaViewState(
    var isLoading: Boolean = false,
    var areas: List<MedicalDirectoryLookupsUI.Area>? = null,
    var governs: List<MedicalDirectoryLookupsUI.Govern>? = null
)
