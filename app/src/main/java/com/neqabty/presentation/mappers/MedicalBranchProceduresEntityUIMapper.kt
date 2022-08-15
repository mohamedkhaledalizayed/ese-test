package com.neqabty.presentation.mappers

import com.neqabty.domain.common.Mapper
import com.neqabty.domain.entities.MedicalBranchProcedureEntity
import com.neqabty.presentation.entities.MedicalBranchProcedureUI
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MedicalBranchProceduresEntityUIMapper @Inject constructor() : Mapper<MedicalBranchProcedureEntity, MedicalBranchProcedureUI>() {

    override fun mapFrom(from: MedicalBranchProcedureEntity): MedicalBranchProcedureUI {
        return MedicalBranchProcedureUI(
            profileId = from.profileId,
            profileName = from.profileName,
            address = from.address,
            phone = from.phone,
            serviceProviderId = from.serviceProviderId,
            medicalProcedurePrice = from.medicalProcedurePrice
        )
    }
}