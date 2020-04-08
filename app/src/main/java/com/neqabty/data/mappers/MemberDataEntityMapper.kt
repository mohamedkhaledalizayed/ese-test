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
                engineerNumber = from.engineerNumber,
                amount = from.amount,
                msg = from.msg
        )
        from.payments?.let {
            var payments: List<MemberEntity.PaymentItem> = it.map { paymentItem ->
                return@map MemberEntity.PaymentItem(paymentItem.quantity, paymentItem.totalPrice, paymentItem.name)
            }
            memberEntity.payments = payments
        }

        from.paymentCreationRequest?.let {
            var sender: MemberEntity.Sender = MemberEntity.Sender(it.sender!!.id, it.sender!!.name, it.sender!!.password)
            var settlementAmount: MemberEntity.SettlementAmounts = MemberEntity.SettlementAmounts(it.settlementAmounts!!.settlementAccountCode, it.settlementAmounts!!.amount)
            var paymentRequest: MemberEntity.PaymentCreationRequest = MemberEntity.PaymentCreationRequest(sender, it.senderRequestNumber, it.serviceCode, settlementAmount, it.currency, it.requestExpiryDate, it.userUniqueIdentifier, it.publicKey)
            memberEntity.paymentCreationRequest = paymentRequest
        }
        return memberEntity
    }
}
