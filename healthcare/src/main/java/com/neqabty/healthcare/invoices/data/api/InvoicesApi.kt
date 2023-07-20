package com.neqabty.healthcare.invoices.data.api

import com.neqabty.healthcare.invoices.data.model.InvoicesModel
import retrofit2.http.GET
import retrofit2.http.Header

interface InvoicesApi {

    @GET("payment/list_payment_invoices")
    suspend fun getAllInvoices(@Header("Authorization") token: String): List<InvoicesModel>

}