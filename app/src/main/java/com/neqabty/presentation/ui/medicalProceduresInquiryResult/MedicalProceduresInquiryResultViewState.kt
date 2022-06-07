package com.neqabty.presentation.ui.medicalProceduresInquiryResult

import com.neqabty.presentation.entities.MedicalBranchProcedureUI

data class MedicalProceduresInquiryResultViewState(
    var isLoading: Boolean = true,
    var branchProcedures: List<MedicalBranchProcedureUI>? = null
)
