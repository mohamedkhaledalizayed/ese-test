package com.neqabty.healthcare.mega.payment.data.source

import com.neqabty.healthcare.core.data.PreferencesHelper
import com.neqabty.healthcare.mega.payment.data.api.PaymentApi
import com.neqabty.healthcare.mega.payment.data.model.PaymentBody
import com.neqabty.healthcare.mega.payment.data.model.PaymentHomeBody
import com.neqabty.healthcare.mega.payment.data.model.branches.EntityBranche
import com.neqabty.healthcare.mega.payment.data.model.inquiryresponse.ReceiptResponse
import com.neqabty.healthcare.mega.payment.data.model.payment.PaymentResponse
import com.neqabty.healthcare.mega.payment.data.model.paymentmethods.PaymentMethodModel
import com.neqabty.healthcare.mega.payment.data.model.paymentstatus.PaymentStatusModel
import com.neqabty.healthcare.mega.payment.data.model.services.ServiceModel
import com.neqabty.healthcare.mega.payment.data.model.servicesaction.ServiceAction
import retrofit2.Response
import javax.inject.Inject

class PaymentDS @Inject constructor(private val paymentApi: PaymentApi, private val sharedPreferences: PreferencesHelper) {

    suspend fun getServices(): List<ServiceModel> {
        return paymentApi.getServices(sharedPreferences.code).services
    }

    suspend fun getServiceActions(code: String): List<ServiceAction> {
        return paymentApi.getServiceActions(code, "Token ${sharedPreferences.token}").serviceActions
    }

    suspend fun getPaymentDetails(id: String,code: String, number: String): Response<ReceiptResponse> {
        return paymentApi.getPaymentDetails(id = id, code = code, number = number, token =  "Token ${sharedPreferences.token}")
    }

    suspend fun payment(paymentBody: PaymentBody): PaymentResponse {
        return paymentApi.payment(paymentBody, token =  "Token ${sharedPreferences.token}")
    }

    suspend fun payment(paymentHomeBody: PaymentHomeBody): PaymentResponse {
        return paymentApi.paymentHome(paymentHomeBody, token =  "Token ${sharedPreferences.token}")
    }

    suspend fun getPaymentMethods(): List<PaymentMethodModel> {
        return paymentApi.getPaymentMethods("Token ${sharedPreferences.token}").paymentMethods
    }

    suspend fun getPaymentStatus(referenceCode: String): PaymentStatusModel {
        return paymentApi.getPaymentStatus(token =  "Token ${sharedPreferences.token}", referenceCode)
    }

    suspend fun getBranches(): List<EntityBranche> {
        return paymentApi.getBranches(code = sharedPreferences.code).data.entityBranches
    }

}