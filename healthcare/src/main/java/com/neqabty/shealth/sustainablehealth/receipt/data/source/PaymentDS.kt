package com.neqabty.shealth.sustainablehealth.receipt.data.source


import com.neqabty.shealth.sustainablehealth.receipt.data.api.PaymentApi
import com.neqabty.shealth.sustainablehealth.receipt.data.model.paymentstatus.PaymentStatusModel
import javax.inject.Inject

class PaymentDS @Inject constructor(private val paymentApi: PaymentApi) {

    suspend fun getPaymentStatus(referenceCode: String): PaymentStatusModel {
        return paymentApi.getPaymentStatus(referenceCode)
    }

}