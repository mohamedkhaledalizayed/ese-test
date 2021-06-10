package com.neqabty.presentation.mappers

import com.neqabty.domain.common.Mapper
import com.neqabty.domain.entities.ChangeUserMobileEntity
import com.neqabty.presentation.entities.ChangeUserMobileUI
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ChangeUserMobileEntityUIMapper @Inject constructor() : Mapper<ChangeUserMobileEntity, ChangeUserMobileUI>() {

    override fun mapFrom(from: ChangeUserMobileEntity): ChangeUserMobileUI {
        return ChangeUserMobileUI(
                name = from.name,
                userNumber = from.userNumber,
                id = from.id,
                mobile = from.mobile,
                msg = from.msg
        )
    }
}
