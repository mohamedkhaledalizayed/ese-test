package com.neqabty.data.mappers

import com.neqabty.data.entities.MedicalComplaintData
import com.neqabty.domain.common.Mapper
import com.neqabty.domain.entities.MedicalComplaintEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MedicalComplaintDataEntityMapper @Inject constructor() : Mapper<MedicalComplaintData, MedicalComplaintEntity>() {

    override fun mapFrom(from: MedicalComplaintData): MedicalComplaintEntity {
        return MedicalComplaintEntity(
                resultType = from.resultType,
                requestID = from.requestID,
                msg = from.msg,
                isSuccess = from.isSuccess
        )
    }
}