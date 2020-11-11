package com.neqabty.presentation.mappers

import com.neqabty.domain.common.Mapper
import com.neqabty.domain.entities.MedicalRenewalEntity
import com.neqabty.domain.entities.MedicalRenewalUpdateEntity
import com.neqabty.presentation.entities.MedicalRenewalUI
import com.neqabty.presentation.entities.MedicalRenewalUpdateUI
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MedicalRenewalUpdateEntityUIMapper @Inject constructor() : Mapper<MedicalRenewalUpdateEntity, MedicalRenewalUpdateUI>() {

    override fun mapFrom(from: MedicalRenewalUpdateEntity): MedicalRenewalUpdateUI {
        return MedicalRenewalUpdateUI(
                requestID = from.requestID
        )
    }
}