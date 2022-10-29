package com.neqabty.data.mappers

import com.neqabty.data.entities.RenewalPaymentData
import com.neqabty.domain.common.Mapper
import com.neqabty.domain.entities.RenewalPaymentEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RenewalPaymentDataEntityMapper @Inject constructor() :
    Mapper<RenewalPaymentData, RenewalPaymentEntity>() {

    override fun mapFrom(from: RenewalPaymentData): RenewalPaymentEntity {
        return RenewalPaymentEntity(
            resultType = from.resultType,
            requestID = from.requestID,
            msg = from.msg,
            amounts = from.amounts?.map { it ->
                RenewalPaymentEntity.AmountItem(
                    name = it.name,
                    id = it.id,
                    cardAmount = it.cardAmount,
                    posAmount = it.posAmount,
                    cardFee = it.cardFee,
                    posFee = it.posFee
                )
            },
            paymentItem = from.paymentItem?.let {
                return@let RenewalPaymentEntity.PaymentItem(
                    paymentRequestNumber = it.paymentRequestNumber,
                    amount = it.amount,
                    name = it.name,
                    engName = it.engName,
                    engNumber = it.engNumber,
                    paymentDetailsItems = it.paymentDetailsItems?.map { item ->
                        RenewalPaymentEntity.PaymentDetailsItem(
                            item.name,
                            item.totalAmount
                        )
                    })
            }
        )
    }
}