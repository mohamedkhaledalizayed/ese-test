package com.neqabty.presentation.mappers

import android.content.Context
import com.neqabty.R
import com.neqabty.domain.common.Mapper
import com.neqabty.domain.entities.RegisteryEntity
import com.neqabty.presentation.entities.RegisteryUI
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RegisteryEntityUIMapper @Inject constructor() : Mapper<RegisteryEntity, RegisteryUI>() {

    override fun mapFrom(from: RegisteryEntity): RegisteryUI {
        return RegisteryUI(
                statusCode = from.statusCode,
                isOwner = from.isOwner == 1,
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