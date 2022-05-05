package com.neqabty.presentation.ui.committees

import com.neqabty.presentation.entities.CommitteesLookupUI
import com.neqabty.presentation.entities.ComplaintTypeUI

data class CommitteesViewState(
    var isLoading: Boolean = false,
    var lookups: CommitteesLookupUI? = null,
    var message: String? = null
)
