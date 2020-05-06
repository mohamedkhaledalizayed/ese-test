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
                oldRefID = from.oldRefID,
                address = from.address,
                birthdate = from.birthdate,
                email = from.email,
                fullName = from.fullName,
                graduationyear = from.graduationyear,
                mobile = from.mobile,
                nationalID = from.nationalID,
                passportNumber = from.passportNumber,
                phone = from.phone
        )
    }
}