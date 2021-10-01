package com.neqabty.presentation.mappers

import com.neqabty.domain.common.Mapper
import com.neqabty.domain.entities.LiteFollowersListEntity
import com.neqabty.presentation.entities.LiteFollowersListUI
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LiteFollowersListEntityUIMapper @Inject constructor() : Mapper<LiteFollowersListEntity, LiteFollowersListUI>() {

    override fun mapFrom(from: LiteFollowersListEntity): LiteFollowersListUI {
        return LiteFollowersListUI(
                id = from.id,
                name = from.name,
                relationTypeName = from.relationTypeName
        )
    }
}
