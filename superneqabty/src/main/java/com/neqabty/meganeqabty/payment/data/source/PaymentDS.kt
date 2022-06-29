package com.neqabty.meganeqabty.payment.data.source

import com.neqabty.meganeqabty.payment.data.api.PaymentApi
import com.neqabty.meganeqabty.payment.data.model.PaymentBody
import com.neqabty.meganeqabty.payment.data.model.inquiryresponse.ReceiptResponse
import com.neqabty.meganeqabty.payment.data.model.payment.PaymentResponse
import com.neqabty.meganeqabty.payment.data.model.paymentmethods.PaymentMethodModel
import com.neqabty.meganeqabty.payment.data.model.paymentstatus.PaymentStatusModel
import com.neqabty.meganeqabty.payment.data.model.services.ServiceModel
import com.neqabty.meganeqabty.payment.data.model.servicesaction.ServiceAction
import javax.inject.Inject

class PaymentDS @Inject constructor(private val paymentApi: PaymentApi) {

    suspend fun getServices(code: String): List<ServiceModel> {
        return paymentApi.getServices(code).services
    }

    suspend fun getServiceActions(code: String): List<ServiceAction> {
        return paymentApi.getServiceActions(code).serviceActions
    }

    suspend fun getPaymentDetails(id: String,code: String, number: String): ReceiptResponse {
        return paymentApi.getPaymentDetails(id = id, code = code, number = number)
    }

    suspend fun payment(paymentBody: PaymentBody): PaymentResponse {
        return paymentApi.payment(paymentBody)
    }

    suspend fun getPaymentMethods(): List<PaymentMethodModel> {
        return paymentApi.getPaymentMethods().paymentMethods
    }

    suspend fun getPaymentStatus(referenceCode: String): PaymentStatusModel {
        return paymentApi.getPaymentStatus(referenceCode)
    }

}