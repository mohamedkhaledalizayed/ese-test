package com.neqabty.healthcare.invoices.data.datasource

import com.neqabty.healthcare.core.data.PreferencesHelper
import com.neqabty.healthcare.invoices.data.api.InvoicesApi
import com.neqabty.healthcare.invoices.data.model.InvoicesModel
import javax.inject.Inject

class InvoicesDataSource @Inject constructor(private val invoicesApi: InvoicesApi, private val preferencesHelper: PreferencesHelper) {

    suspend fun getAllInvoices(): List<InvoicesModel>{
        return invoicesApi.getAllInvoices(token = "Token ${preferencesHelper.token}")
    }
}