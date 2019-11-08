package com.neqabty.presentation.mappers

import com.neqabty.domain.common.Mapper
import com.neqabty.domain.entities.VerifyUserDataEntity
import com.neqabty.presentation.entities.VerifyUserDataUI
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class VerifyUserDataEntityUIMapper @Inject constructor() : Mapper<VerifyUserDataEntity, VerifyUserDataUI>() {

    override fun mapFrom(from: VerifyUserDataEntity): VerifyUserDataUI {
        return VerifyUserDataUI(
                code = from.code
        )
    }
}