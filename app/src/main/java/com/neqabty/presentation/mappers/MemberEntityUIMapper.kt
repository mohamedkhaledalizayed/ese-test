package com.neqabty.presentation.mappers

import com.neqabty.domain.common.Mapper
import com.neqabty.domain.entities.MemberEntity
import com.neqabty.presentation.entities.MemberUI
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MemberEntityUIMapper @Inject constructor() : Mapper<MemberEntity, MemberUI>() {

    override fun mapFrom(from: MemberEntity): MemberUI {
        return MemberUI(
                engineerID = from.engineerID,
                code = from.code,
                amount = from.amount,
                billDate = from.billDate,
                engineerName = from.engineerName,
                expirationDate = from.expirationDate,
                interfaceLanguage = from.interfaceLanguage,
                lastPaymentDate = from.lastPaymentDate,
                message = "",
                paymentType = from.paymentType
        )
    }
}
