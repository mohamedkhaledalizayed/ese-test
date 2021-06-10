package com.neqabty.data.mappers

import com.neqabty.data.entities.ChangeUserMobileData
import com.neqabty.domain.common.Mapper
import com.neqabty.domain.entities.ChangeUserMobileEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ChangeUserMobileDataEntityMapper @Inject constructor() : Mapper<ChangeUserMobileData, ChangeUserMobileEntity>() {

    override fun mapFrom(from: ChangeUserMobileData): ChangeUserMobileEntity {
        return ChangeUserMobileEntity(
                name = from.name,
                userNumber = from.userNumber,
                id = from.id,
                mobile = from.mobile,
                msg = from.msg
        )
    }
}
