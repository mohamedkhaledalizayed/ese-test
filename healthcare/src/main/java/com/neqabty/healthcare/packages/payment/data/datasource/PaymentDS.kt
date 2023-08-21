package com.neqabty.healthcare.packages.payment.data.datasource

import com.neqabty.healthcare.core.data.PreferencesHelper
import com.neqabty.healthcare.packages.payment.data.api.PaymentApi
import com.neqabty.healthcare.packages.payment.data.model.SehaPaymentBody
import com.neqabty.healthcare.packages.payment.data.model.paymentmethods.PaymentMethodsResponse
import com.neqabty.healthcare.packages.payment.data.model.sehapayment.SehaPaymentResponse
import retrofit2.Response
import javax.inject.Inject

class PaymentDS @Inject constructor(private val paymentApi: PaymentApi, private val sharedPreferences: PreferencesHelper) {

    suspend fun payment(paymentBody: SehaPaymentBody): Response<SehaPaymentResponse> {
        return paymentApi.payment(paymentBody, token =  "Token ${sharedPreferences.token}")
    }

    suspend fun getPaymentMethods(code: String): PaymentMethodsResponse {
        return paymentApi.getPaymentMethods("Token ${sharedPreferences.token}", code = code)
    }

}