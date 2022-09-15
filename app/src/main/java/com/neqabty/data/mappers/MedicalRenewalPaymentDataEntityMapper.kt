package com.neqabty.data.mappers

import com.neqabty.data.entities.MedicalRenewalPaymentData
import com.neqabty.domain.common.Mapper
import com.neqabty.domain.entities.MedicalRenewalPaymentEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MedicalRenewalPaymentDataEntityMapper @Inject constructor() : Mapper<MedicalRenewalPaymentData, MedicalRenewalPaymentEntity>() {

    override fun mapFrom(from: MedicalRenewalPaymentData): MedicalRenewalPaymentEntity {
        return MedicalRenewalPaymentEntity(
                resultType = from.resultType,
                requestID = from.requestID,
                msg = from.msg,
                amounts = from.amounts?.map { it ->
                    MedicalRenewalPaymentEntity.AmountItem(
                        name = it.name,
                        id = it.id,
                        cardAmount = it.cardAmount,
                        posAmount = it.posAmount
                    )
                },
                paymentItem = from.paymentItem?.let { return@let MedicalRenewalPaymentEntity.PaymentItem(paymentRequestNumber = it.paymentRequestNumber, amount = it.amount, name = it.name, engName = it.engName, engNumber = it.engNumber, paymentDetailsItems = it.paymentDetailsItems?.map { item -> MedicalRenewalPaymentEntity.PaymentDetailsItem(item.name, item.totalAmount) }) }
        )
    }
}