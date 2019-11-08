package com.neqabty.presentation.mappers

import com.neqabty.domain.common.Mapper
import com.neqabty.domain.entities.UpdateUserDataEntity
import com.neqabty.presentation.entities.UpdateUserDataUI
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UpdateUserDataEntityUIMapper @Inject constructor() : Mapper<UpdateUserDataEntity, UpdateUserDataUI>() {

    override fun mapFrom(from: UpdateUserDataEntity): UpdateUserDataUI {
        return UpdateUserDataUI(
                message = from.message
        )
    }
}