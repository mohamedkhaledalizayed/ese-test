package com.neqabty.data.mappers

import com.neqabty.data.entities.UpdateUserData
import com.neqabty.domain.common.Mapper
import com.neqabty.domain.entities.UpdateUserDataEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UpdateUserDataEntityMapper @Inject constructor() : Mapper<UpdateUserData, UpdateUserDataEntity>() {

    override fun mapFrom(from: UpdateUserData): UpdateUserDataEntity {
        return UpdateUserDataEntity(
                message = from.message
        )
    }
}
