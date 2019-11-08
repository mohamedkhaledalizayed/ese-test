package com.neqabty.data.mappers

import com.neqabty.data.entities.InquireUpdateUserData
import com.neqabty.domain.common.Mapper
import com.neqabty.domain.entities.InquireUpdateUserDataEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class InquireUpdateUserDataEntityMapper @Inject constructor() : Mapper<InquireUpdateUserData, InquireUpdateUserDataEntity>() {

    override fun mapFrom(from: InquireUpdateUserData): InquireUpdateUserDataEntity {
        return InquireUpdateUserDataEntity(
                id = from.id,
                fullName = from.fullName,
                birthdate = from.birthdate,
                createdAt = from.createdAt,
                nationalID = from.nationalID,
                nationalVerified = from.nationalVerified,
                oldRefID = from.oldRefID,
                phoneCode = from.phoneCode,
                phoneNumber = from.phoneNumber,
                phoneVerified = from.phoneVerified,
                updatedAt = from.updatedAt
        )
    }
}
