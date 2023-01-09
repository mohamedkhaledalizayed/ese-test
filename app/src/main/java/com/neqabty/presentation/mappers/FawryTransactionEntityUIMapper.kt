package com.neqabty.presentation.mappers

import com.neqabty.domain.common.Mapper
import com.neqabty.domain.entities.FawryTransactionEntity
import com.neqabty.presentation.entities.FawryTransactionUI
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FawryTransactionEntityUIMapper @Inject constructor() : Mapper<FawryTransactionEntity, FawryTransactionUI>() {

    override fun mapFrom(from: FawryTransactionEntity): FawryTransactionUI {
        return FawryTransactionUI(
            referenceNumber = from.referenceNumber
        )
    }
}
