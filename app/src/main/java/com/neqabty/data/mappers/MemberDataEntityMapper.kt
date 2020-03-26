package com.neqabty.data.mappers

import com.neqabty.data.entities.MemberData
import com.neqabty.domain.common.Mapper
import com.neqabty.domain.entities.MemberEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MemberDataEntityMapper @Inject constructor() : Mapper<MemberData, MemberEntity>() {

    override fun mapFrom(from: MemberData): MemberEntity {
        val memberEntity: MemberEntity = MemberEntity(
                requestID = from.requestID,
                engineerName = from.engineerName,
                amount = from.amount,
                msg = from.msg
        )
        from.payments?.let {
            var payments: List<MemberEntity.PaymentItem> = it.map { paymentItem ->
                return@map MemberEntity.PaymentItem(paymentItem.quantity, paymentItem.totalPrice, paymentItem.name)}
            memberEntity.payments = payments
        }

        return memberEntity
    }
}
