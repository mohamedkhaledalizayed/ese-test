package com.neqabty.presentation.entities

data class MedicalBranchProcedureUI(
    var profileId: Int = 0,
    var profileName: String?,
    var address: String?,
    var phone: String?,
    var serviceProviderId: Int?,
    var medicalProcedurePrice: Int?
)