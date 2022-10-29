package com.neqabty.presentation.mappers

import com.neqabty.domain.common.Mapper
import com.neqabty.domain.entities.RenewalPaymentEntity
import com.neqabty.presentation.entities.RenewalPaymentUI
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RenewalPaymentEntityUIMapper @Inject constructor() :
    Mapper<RenewalPaymentEntity, RenewalPaymentUI>() {

    override fun mapFrom(from: RenewalPaymentEntity): RenewalPaymentUI {
        return RenewalPaymentUI(
            resultType = from.resultType,
            requestID = from.requestID,
            msg = from.msg ?: "",
            amounts = from.amounts?.map { it ->
                RenewalPaymentUI.AmountItem(
                    name = it.name,
                    id = it.id,
                    cardAmount = it.cardAmount,
                    posAmount = it.posAmount,
                    cardFee = it.cardFee,
                    posFee = it.posFee
                )
            },
            paymentItem = from.paymentItem?.let {
                return@let RenewalPaymentUI.PaymentItem(
                    paymentRequestNumber = it.paymentRequestNumber,
                    amount = it.amount,
                    name = it.name,
                    engName = it.engName,
                    engNumber = it.engNumber,
                    paymentDetailsItems = it.paymentDetailsItems?.map { item ->
                        RenewalPaymentUI.PaymentDetailsItem(
                            item.name,
                            item.totalAmount
                        )
                    })
            }
        )
    }
}