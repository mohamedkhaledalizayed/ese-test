package com.neqabty.data.mappers

import com.neqabty.data.entities.FawryTransactionData
import com.neqabty.domain.common.Mapper
import com.neqabty.domain.entities.FawryTransactionEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FawryTransactionDataEntityMapper @Inject constructor() : Mapper<FawryTransactionData, FawryTransactionEntity>() {

    override fun mapFrom(from: FawryTransactionData): FawryTransactionEntity {
        return FawryTransactionEntity(
            referenceNumber = from.referenceNumber
        )
    }
}
