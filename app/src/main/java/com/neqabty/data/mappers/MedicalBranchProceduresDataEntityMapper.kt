package com.neqabty.data.mappers

import com.neqabty.data.entities.MedicalBranchProcedureData
import com.neqabty.domain.common.Mapper
import com.neqabty.domain.entities.MedicalBranchProcedureEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MedicalBranchProceduresDataEntityMapper @Inject constructor() : Mapper<MedicalBranchProcedureData, MedicalBranchProcedureEntity>() {

    override fun mapFrom(from: MedicalBranchProcedureData): MedicalBranchProcedureEntity {
        return MedicalBranchProcedureEntity(
            profileId = from.profileId,
            profileName = from.profileName,
            address = from.address,
            phone = from.phone,
            serviceProviderId = from.serviceProviderId,
            medicalProcedurePrice = from.medicalProcedurePrice
        )
    }
}