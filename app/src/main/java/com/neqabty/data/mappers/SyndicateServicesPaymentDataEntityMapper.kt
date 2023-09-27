package com.neqabty.data.mappers

import com.neqabty.data.entities.SyndicateServicesPaymentData
import com.neqabty.domain.common.Mapper
import com.neqabty.domain.entities.SyndicateServicesPaymentEntity
import javax.inject.Inject

class SyndicateServicesPaymentDataEntityMapper @Inject constructor() :
    Mapper<SyndicateServicesPaymentData, SyndicateServicesPaymentEntity>() {

    override fun mapFrom(from: SyndicateServicesPaymentData): SyndicateServicesPaymentEntity {
        return SyndicateServicesPaymentEntity(
            resultType = from.resultType,
            requestID = from.requestID,
            msg = from.msg,
            amounts = from.amounts?.map { it ->
                SyndicateServicesPaymentEntity.AmountItem(
                    name = it.name,
                    id = it.id,
                    cardAmount = it.cardAmount,
                    posAmount = it.posAmount
                )
            },
            paymentItem = from.paymentItem?.let {
                return@let SyndicateServicesPaymentEntity.PaymentItem(
                    paymentRequestNumber = it.paymentRequestNumber,
                    amount = it.amount,
                    name = it.name,
                    engName = it.engName,
                    engNumber = it.engNumber,
                    paymentDetailsItems = it.paymentDetailsItems?.map { item ->
                        SyndicateServicesPaymentEntity.PaymentDetailsItem(
                            item.name,
                            item.totalAmount
                        )
                    })
            }
        )
    }
}