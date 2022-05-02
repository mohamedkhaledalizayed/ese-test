package com.neqabty.data.mappers

import com.neqabty.data.entities.SyndicateBranchData
import com.neqabty.domain.common.Mapper
import com.neqabty.domain.entities.SyndicateBranchEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SyndicateBranchDataEntityMapper @Inject constructor() : Mapper<SyndicateBranchData, SyndicateBranchEntity>() {

    override fun mapFrom(from: SyndicateBranchData): SyndicateBranchEntity {
        return SyndicateBranchEntity(
                id = from.id,
                name = from.name,
                location = from.location,
                address = from.address,
                phone = from.phone
        )
    }
}
