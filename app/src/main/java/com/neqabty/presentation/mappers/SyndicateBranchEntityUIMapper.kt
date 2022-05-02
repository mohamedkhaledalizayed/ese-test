package com.neqabty.presentation.mappers

import com.neqabty.domain.common.Mapper
import com.neqabty.domain.entities.SyndicateBranchEntity
import com.neqabty.presentation.entities.SyndicateBranchUI
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SyndicateBranchEntityUIMapper @Inject constructor() : Mapper<SyndicateBranchEntity, SyndicateBranchUI>() {

    override fun mapFrom(from: SyndicateBranchEntity): SyndicateBranchUI {
        return SyndicateBranchUI(
                id = from.id,
                name = from.name,
                location = from.location,
                address = from.address,
                phone = from.phone
        )
    }
}