package com.neqabty.presentation.mappers

import com.neqabty.domain.common.Mapper
import com.neqabty.domain.entities.MedicalRenewalPaymentEntity
import com.neqabty.presentation.entities.MedicalRenewalPaymentUI
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MedicalRenewalPaymentEntityUIMapper @Inject constructor() : Mapper<MedicalRenewalPaymentEntity, MedicalRenewalPaymentUI>() {

    override fun mapFrom(from: MedicalRenewalPaymentEntity): MedicalRenewalPaymentUI {
        return MedicalRenewalPaymentUI(
                resultType = from.resultType,
                requestID = from.requestID,
                paymentItem = from.paymentItem?.let { return@let MedicalRenewalPaymentUI.PaymentItem(paymentRequestNumber = it.paymentRequestNumber, amount = it.amount, name = it.name, engName = it.engName, engNumber = it.engNumber, paymentDetailsItems = it.paymentDetailsItems?.map { item -> MedicalRenewalPaymentUI.PaymentDetailsItem(item.name, item.totalAmount) }) }
        )
    }
}