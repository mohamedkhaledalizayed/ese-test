package com.neqabty.data.mappers

import com.neqabty.data.entities.MedicalRenewalPaymentData
import com.neqabty.domain.common.Mapper
import com.neqabty.domain.entities.MedicalRenewalPaymentEntity
import com.neqabty.presentation.entities.MedicalRenewalPaymentUI
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MedicalRenewalPaymentDataEntityMapper @Inject constructor() : Mapper<MedicalRenewalPaymentData, MedicalRenewalPaymentEntity>() {

    override fun mapFrom(from: MedicalRenewalPaymentData): MedicalRenewalPaymentEntity {
        return MedicalRenewalPaymentEntity(
                requestID = from.requestID,
                paymentItem = from.paymentItem?.let { return@let MedicalRenewalPaymentEntity.PaymentItem(paymentRequestNumber = it.paymentRequestNumber, amount = it.amount, name = it.name) }
        )
    }
}