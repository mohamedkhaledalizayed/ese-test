package com.neqabty.presentation.ui.medicalProceduresInquiry

import com.neqabty.presentation.entities.MedicalProceduresInquiryLookupsUI

data class MedicalProceduresInquiryViewState(
    var isLoading: Boolean = true,
    var governs: List<MedicalProceduresInquiryLookupsUI.Govern>? = null,
    var areas: List<MedicalProceduresInquiryLookupsUI.Area>? = null,
    var categories: List<MedicalProceduresInquiryLookupsUI.ProcedureCategory>? = null,
    var relationTypes: List<MedicalProceduresInquiryLookupsUI.RelationType>? = null
)
