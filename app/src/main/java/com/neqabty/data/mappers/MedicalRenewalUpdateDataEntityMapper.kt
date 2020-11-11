package com.neqabty.data.mappers

import com.neqabty.data.entities.MedicalRenewalData
import com.neqabty.data.entities.MedicalRenewalUpdateData
import com.neqabty.domain.common.Mapper
import com.neqabty.domain.entities.MedicalRenewalEntity
import com.neqabty.domain.entities.MedicalRenewalUpdateEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MedicalRenewalUpdateDataEntityMapper @Inject constructor() : Mapper<MedicalRenewalUpdateData, MedicalRenewalUpdateEntity>() {

    override fun mapFrom(from: MedicalRenewalUpdateData): MedicalRenewalUpdateEntity {
        return MedicalRenewalUpdateEntity(
                requestID = from.requestID
        )
    }
}