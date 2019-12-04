package com.neqabty.data.mappers

import com.neqabty.data.entities.ClaimingValidationData
import com.neqabty.domain.common.Mapper
import com.neqabty.domain.entities.ClaimingValidationEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ClaimingValidationDataEntityMapper @Inject constructor() : Mapper<ClaimingValidationData, ClaimingValidationEntity>() {

    override fun mapFrom(from: ClaimingValidationData): ClaimingValidationEntity {
        return ClaimingValidationEntity(
                engineerName = from.engineerName,
                code = from.code,
                message = from.message
        )
    }
}
