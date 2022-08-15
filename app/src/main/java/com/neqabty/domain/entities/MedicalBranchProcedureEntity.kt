package com.neqabty.domain.entities

data class MedicalBranchProcedureEntity(
    var profileId: Int = 0,
    var profileName: String?,
    var address: String?,
    var phone: String?,
    var serviceProviderId: Int?,
    var medicalProcedurePrice: Int?
)