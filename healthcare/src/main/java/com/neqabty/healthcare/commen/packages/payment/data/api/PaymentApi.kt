package com.neqabty.healthcare.commen.packages.payment.data.api

import com.neqabty.healthcare.commen.packages.payment.data.model.paymentmethods.PaymentMethodsResponse
import com.neqabty.healthcare.commen.packages.payment.data.model.SehaPaymentBody
import com.neqabty.healthcare.commen.packages.payment.data.model.sehapayment.SehaPaymentResponse
import retrofit2.Response
import retrofit2.http.*

interface PaymentApi {

    @POST("payment/paymentV2/")
    suspend fun payment(@Body paymentBody: SehaPaymentBody,
                        @Header("Authorization") token: String): Response<SehaPaymentResponse>

    @GET("payment/get_gateway_parameter")
    suspend fun getPaymentMethods(@Header("Authorization") token: String, @Query("service_code") code: String): PaymentMethodsResponse


}