package com.neqabty.presentation.ui.medicalProceduresInquiryCont

import com.neqabty.presentation.entities.MedicalProcedureUI
import com.neqabty.presentation.entities.MedicalProceduresInquiryLookupsUI

data class MedicalProceduresInquiryContViewState(
    var isLoading: Boolean = false,
    var categories: List<MedicalProceduresInquiryLookupsUI.ProcedureCategory>? = null,
    var subcategories: List<MedicalProceduresInquiryLookupsUI.ProcedureCategory>? = null,
    var procedures: List<MedicalProcedureUI>? = null
)
