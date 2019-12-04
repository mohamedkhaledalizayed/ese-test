package com.neqabty.presentation.mappers

import com.neqabty.data.entities.ClaimingValidationData
import com.neqabty.domain.common.Mapper
import com.neqabty.domain.entities.ClaimingValidationEntity
import com.neqabty.presentation.entities.ClaimingValidationUI
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ClaimingValidationEntityUIMapper @Inject constructor() : Mapper<ClaimingValidationEntity, ClaimingValidationUI>() {

    override fun mapFrom(from: ClaimingValidationEntity): ClaimingValidationUI {
        return ClaimingValidationUI(
                engineerName = from.engineerName,
                code = from.code,
                message = from.message
        )
    }
}
