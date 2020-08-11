package com.neqabty.presentation.mappers

import com.neqabty.domain.common.Mapper
import com.neqabty.domain.entities.MemberEntity
import com.neqabty.presentation.entities.MemberUI
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MemberEntityUIMapper @Inject constructor() : Mapper<MemberEntity, MemberUI>() {

    override fun mapFrom(from: MemberEntity): MemberUI {
        val memberUI: MemberUI = MemberUI(
                requestID = from.requestID,
                engineerName = from.engineerName,
                engineerNumber = from.engineerNumber,
                amount = from.amount,
                msg = from.msg
        )
        from.payments?.let {
            var payments: List<MemberUI.PaymentItem> = it.map { paymentItem ->
                return@map MemberUI.PaymentItem(paymentItem.quantity, paymentItem.totalPrice, paymentItem.name) }
            memberUI.payments = payments
        }

        from.paymentCreationRequest?.let {
            var sender: MemberUI.Sender = MemberUI.Sender(it.sender!!.id, it.sender!!.name, it.sender!!.password)
            var settlementAmount: MemberUI.SettlementAmounts = MemberUI.SettlementAmounts(it.settlementAmounts!!.settlementAccountCode, it.settlementAmounts!!.amount)
            var paymentRequest: MemberUI.PaymentCreationRequest = MemberUI.PaymentCreationRequest(sender, it.senderRequestNumber, it.serviceCode, settlementAmount, it.currency, it.requestExpiryDate, it.userUniqueIdentifier, it.publicKey)
            memberUI.paymentCreationRequest = paymentRequest
        }
        return memberUI
    }
}
