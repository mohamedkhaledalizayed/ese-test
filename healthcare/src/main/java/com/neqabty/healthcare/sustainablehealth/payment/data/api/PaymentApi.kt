package com.neqabty.healthcare.sustainablehealth.payment.data.api

import com.neqabty.healthcare.mega.payment.data.model.paymentmethods.PaymentMethodsResponse
import com.neqabty.healthcare.sustainablehealth.payment.data.model.SehaPaymentBody
import com.neqabty.healthcare.sustainablehealth.payment.data.model.sehapayment.SehaPaymentResponse
import retrofit2.http.*

interface PaymentApi {

    @POST("payments")
    suspend fun payment(@Body paymentBody: SehaPaymentBody,
                        @Header("Authorization") token: String): SehaPaymentResponse

    @GET("payment_methods")
    suspend fun getPaymentMethods(@Header("Authorization") token: String): PaymentMethodsResponse


}