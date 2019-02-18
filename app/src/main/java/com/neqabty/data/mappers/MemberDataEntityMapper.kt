package com.neqabty.data.mappers

import com.neqabty.data.entities.MemberData
import com.neqabty.domain.common.Mapper
import com.neqabty.domain.entities.MemberEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MemberDataEntityMapper @Inject constructor() : Mapper<MemberData, MemberEntity>() {

    override fun mapFrom(from: MemberData): MemberEntity {
        return MemberEntity(
                engineerID = from.engineerID,
                code = from.code,
                amount = from.amount,
                billDate = from.billDate,
                engineerName = from.engineerName,
                expirationDate = from.expirationDate,
                interfaceLanguage = from.interfaceLanguage,
                lastPaymentDate = from.lastPaymentDate,
                message = from.message,
                paymentType = from.paymentType
        )
    }
}
