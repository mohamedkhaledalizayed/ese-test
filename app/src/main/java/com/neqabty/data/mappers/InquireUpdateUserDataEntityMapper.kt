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
