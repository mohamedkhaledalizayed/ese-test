package com.neqabty.healthcare.modules.payment.data.source

import com.neqabty.core.data.PreferencesHelper
import com.neqabty.healthcare.modules.payment.data.api.PaymentApi
import com.neqabty.healthcare.modules.payment.data.model.SehaPaymentBody
import com.neqabty.healthcare.modules.payment.data.model.sehapayment.SehaPaymentResponse
import com.neqabty.meganeqabty.payment.data.model.paymentmethods.PaymentMethodModel
import javax.inject.Inject

class SehaPaymentDS @Inject constructor(private val paymentApi: PaymentApi, private val sharedPreferences: PreferencesHelper) {

    suspend fun payment(paymentBody: SehaPaymentBody): SehaPaymentResponse {
        return paymentApi.payment(paymentBody, token =  "Token ${sharedPreferences.token}")
    }

    suspend fun getPaymentMethods(): List<PaymentMethodModel> {
        return paymentApi.getPaymentMethods("Token ${sharedPreferences.token}").paymentMethods
    }

}