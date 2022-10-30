package com.neqabty.healthcare.sustainablehealth.receipt.data.api


import com.neqabty.healthcare.sustainablehealth.receipt.data.model.paymentstatus.PaymentStatusModel
import retrofit2.http.*

interface PaymentApi {

    @GET("transactions/get/{transaction_id}")
    suspend fun getPaymentStatus(@Path("transaction_id") transaction_id: String): PaymentStatusModel

}