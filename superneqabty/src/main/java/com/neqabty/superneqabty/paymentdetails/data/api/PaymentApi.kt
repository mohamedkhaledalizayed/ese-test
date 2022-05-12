package com.neqabty.superneqabty.paymentdetails.data.api

import com.neqabty.superneqabty.paymentdetails.data.model.PaymentBody
import com.neqabty.superneqabty.paymentdetails.data.model.inquiryresponse.ReceiptResponse
import com.neqabty.superneqabty.paymentdetails.data.model.payment.PaymentResponse
import com.neqabty.superneqabty.paymentdetails.data.model.paymentdetails.PaymentDetails
import com.neqabty.superneqabty.paymentdetails.data.model.paymentmethods.PaymentMethodsResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface PaymentApi {

    @GET("api/payments/v2/inquiry/{id}/{number}")
    suspend fun getPaymentDetails(@Path("id") id: String, @Path("number") number: String): ReceiptResponse

    @POST("api/payments")
    suspend fun getPaymentInfo(@Body paymentBody: PaymentBody): PaymentResponse

    @GET("api/payment_methods")
    suspend fun getPaymentMethods(): PaymentMethodsResponse

    @GET("api/transactions/get/{transaction_id}")
    suspend fun getPaymentDetails(@Path("transaction_id") transaction_id: String): PaymentDetails

}