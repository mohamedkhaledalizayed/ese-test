package com.neqabty.superneqabty.paymentdetails.data.api

import com.neqabty.superneqabty.paymentdetails.data.model.ReceiptResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface PaymentApi {

    @GET("api/payments/v1/inquiry/{id}/{number}")
    suspend fun getPaymentDetails(@Path("id") id: String, @Path("number") number: String): ReceiptResponse

}