package com.neqabty.presentation.mappers

import com.neqabty.domain.common.Mapper
import com.neqabty.domain.entities.InquireUpdateUserDataEntity
import com.neqabty.presentation.entities.InquireUpdateUserDataUI
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class InquireUpdateUserDataEntityUIMapper @Inject constructor() : Mapper<InquireUpdateUserDataEntity, InquireUpdateUserDataUI>() {

    override fun mapFrom(from: InquireUpdateUserDataEntity): InquireUpdateUserDataUI {
        return InquireUpdateUserDataUI(
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