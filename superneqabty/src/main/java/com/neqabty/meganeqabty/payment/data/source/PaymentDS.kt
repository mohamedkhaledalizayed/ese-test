package com.neqabty.meganeqabty.payment.data.source

import com.neqabty.login.core.utils.PreferencesHelper
import com.neqabty.meganeqabty.payment.data.api.PaymentApi
import com.neqabty.meganeqabty.payment.data.model.PaymentBody
import com.neqabty.meganeqabty.payment.data.model.branches.EntityBranche
import com.neqabty.meganeqabty.payment.data.model.inquiryresponse.ReceiptResponse
import com.neqabty.meganeqabty.payment.data.model.payment.PaymentResponse
import com.neqabty.meganeqabty.payment.data.model.paymentmethods.PaymentMethodModel
import com.neqabty.meganeqabty.payment.data.model.paymentstatus.PaymentStatusModel
import com.neqabty.meganeqabty.payment.data.model.services.ServiceModel
import com.neqabty.meganeqabty.payment.data.model.servicesaction.ServiceAction
import javax.inject.Inject

class PaymentDS @Inject constructor(private val paymentApi: PaymentApi, private val sharedPreferences: PreferencesHelper) {

    suspend fun getServices(): List<ServiceModel> {
        return paymentApi.getServices(sharedPreferences.code).services
    }

    suspend fun getServiceActions(code: String): List<ServiceAction> {
        return paymentApi.getServiceActions(code, "Token ${sharedPreferences.token}").serviceActions
    }

    suspend fun getPaymentDetails(id: String,code: String, number: String): ReceiptResponse {
        return paymentApi.getPaymentDetails(id = id, code = code, number = number, token =  "Token ${sharedPreferences.token}")
    }

    suspend fun payment(paymentBody: PaymentBody): PaymentResponse {
        return paymentApi.payment(paymentBody, token =  "Token ${sharedPreferences.token}")
    }

    suspend fun getPaymentMethods(): List<PaymentMethodModel> {
        return paymentApi.getPaymentMethods("Token ${sharedPreferences.token}").paymentMethods
    }

    suspend fun getPaymentStatus(referenceCode: String): PaymentStatusModel {
        return paymentApi.getPaymentStatus(referenceCode)
    }

    suspend fun getBranches(): List<EntityBranche> {
        return paymentApi.getBranches(code = sharedPreferences.code).data.entityBranches
    }

}