package com.neqabty.data.mappers

import com.neqabty.data.entities.VerifyUserData
import com.neqabty.domain.common.Mapper
import com.neqabty.domain.entities.VerifyUserDataEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class VerifyUserDataEntityMapper @Inject constructor() : Mapper<VerifyUserData, VerifyUserDataEntity>() {

    override fun mapFrom(from: VerifyUserData): VerifyUserDataEntity {
        return VerifyUserDataEntity(
                code = from.code
        )
    }
}
