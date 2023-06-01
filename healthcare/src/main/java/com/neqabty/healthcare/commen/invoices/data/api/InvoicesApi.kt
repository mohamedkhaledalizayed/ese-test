package com.neqabty.healthcare.commen.invoices.data.api

import com.neqabty.healthcare.commen.invoices.data.model.InvoicesModel
import retrofit2.http.GET
import retrofit2.http.Header

interface InvoicesApi {

    @GET("payment/list_payment_invoices")
    suspend fun getAllInvoices(@Header("Authorization") token: String): List<InvoicesModel>

}