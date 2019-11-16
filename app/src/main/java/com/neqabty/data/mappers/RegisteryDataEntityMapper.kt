package com.neqabty.data.mappers

import com.neqabty.data.entities.RegisteryData
import com.neqabty.domain.common.Mapper
import com.neqabty.domain.entities.RegisteryEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RegisteryDataEntityMapper @Inject constructor() : Mapper<RegisteryData, RegisteryEntity>() {

    override fun mapFrom(from: RegisteryData): RegisteryEntity {
        return RegisteryEntity(
                statusCode = from.statusCode,
                isOwner = from.isOwner,
                birthDate = from.birthDate,
                contactID = from.contactID,
                engID = from.engID,
                fullName = from.fullName,
                lastRenewYear = from.lastRenewYear,
                mobile = from.mobile,
                regDataStatusID = from.regDataStatusID,
                registerOffice = from.registerOffice,
                registryDataID = from.registryDataID,
                registryEngineerID = from.registryEngineerID,
                registryTypeID = from.registryTypeID,
                msg = from.msg
        )
    }
}
