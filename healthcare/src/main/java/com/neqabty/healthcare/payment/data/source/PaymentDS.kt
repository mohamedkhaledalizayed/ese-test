package com.neqabty.healthcare.payment.data.source

import com.neqabty.healthcare.core.data.PreferencesHelper
import com.neqabty.healthcare.payment.data.api.PaymentApi
import com.neqabty.healthcare.payment.data.model.branches.EntityBranche
import com.neqabty.healthcare.payment.data.model.inquiryresponse.InquiryModel
import com.neqabty.healthcare.payment.data.model.payment.PaymentModel
import com.neqabty.healthcare.payment.data.model.paymentstatus.PaymentStatusModel
import com.neqabty.healthcare.payment.data.model.services.ServiceModel
import com.neqabty.healthcare.payment.data.model.servicesaction.ServiceAction
import retrofit2.Response
import javax.inject.Inject

class PaymentDS @Inject constructor(private val paymentApi: PaymentApi, private val sharedPreferences: PreferencesHelper) {

    suspend fun getServices(): List<ServiceModel> {
        return paymentApi.getServices(sharedPreferences.code).services
    }

    suspend fun getServiceActions(code: String): List<ServiceAction> {
        return paymentApi.getServiceActions(code, "Token ${sharedPreferences.token}").serviceActions
    }

    suspend fun getPaymentDetails(id: String,code: String, number: String): Response<InquiryModel> {
        return paymentApi.getPaymentDetails(id = id, code = code, number = number, token =  "Token ${sharedPreferences.token}")
    }

    suspend fun payment(paymentBody: Any): PaymentModel {
        return paymentApi.payment(paymentBody, token =  "Token ${sharedPreferences.token}")
    }

    suspend fun getPaymentStatus(referenceCode: String): PaymentStatusModel {
        return paymentApi.getPaymentStatus(token =   "Token ${sharedPreferences.token}", referenceCode)
    }

    suspend fun getBranches(): List<EntityBranche> {
        return paymentApi.getBranches(code = sharedPreferences.code).data.entityBranches
    }

}