package com.neqabty.presentation.mappers

import com.neqabty.domain.common.Mapper
import com.neqabty.domain.entities.MedicalComplaintEntity
import com.neqabty.presentation.entities.MedicalComplaintUI
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MedicalComplaintEntityUIMapper @Inject constructor() : Mapper<MedicalComplaintEntity, MedicalComplaintUI>() {

    override fun mapFrom(from: MedicalComplaintEntity): MedicalComplaintUI {
        return MedicalComplaintUI(
                resultType = from.resultType,
                requestID = from.requestID,
                msg = from.msg?: "",
                isSuccess = from.isSuccess
        )
    }
}