package com.neqabty.data.mappers

import com.neqabty.data.entities.LiteFollowersListData
import com.neqabty.domain.common.Mapper
import com.neqabty.domain.entities.LiteFollowersListEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LiteFollowersListDataEntityMapper @Inject constructor() : Mapper<LiteFollowersListData, LiteFollowersListEntity>() {

    override fun mapFrom(from: LiteFollowersListData): LiteFollowersListEntity {
        return LiteFollowersListEntity(
            id = from.id,
            name = from.name,
            relationTypeName = from.relationTypeName
        )
    }
}
