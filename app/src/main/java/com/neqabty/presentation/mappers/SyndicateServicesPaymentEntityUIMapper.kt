package com.neqabty.presentation.mappers

import com.neqabty.domain.common.Mapper
import com.neqabty.domain.entities.SyndicateServicesPaymentEntity
import com.neqabty.presentation.entities.SyndicateServicesPaymentUI
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SyndicateServicesPaymentEntityUIMapper @Inject constructor() :
    Mapper<SyndicateServicesPaymentEntity, SyndicateServicesPaymentUI>() {

    override fun mapFrom(from: SyndicateServicesPaymentEntity): SyndicateServicesPaymentUI {
        return SyndicateServicesPaymentUI(
            resultType = from.resultType,
            requestID = from.requestID,
            msg = from.msg ?: "",
            amounts = from.amounts?.map { it ->
                SyndicateServicesPaymentUI.AmountItem(
                    name = it.name,
                    id = it.id,
                    cardAmount = it.cardAmount,
                    posAmount = it.posAmount
                )
            },
            paymentItem = from.paymentItem?.let {
                return@let SyndicateServicesPaymentUI.PaymentItem(
                    paymentRequestNumber = it.paymentRequestNumber,
                    amount = it.amount,
                    name = it.name,
                    engName = it.engName,
                    engNumber = it.engNumber,
                    paymentDetailsItems = it.paymentDetailsItems?.map { item ->
                        SyndicateServicesPaymentUI.PaymentDetailsItem(
                            item.name,
                            item.totalAmount
                        )
                    })
            }
        )
    }
}