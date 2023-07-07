package com.neqabty.healthcare.commen.invoices.data.datasource

import com.neqabty.healthcare.commen.invoices.data.api.InvoicesApi
import com.neqabty.healthcare.commen.invoices.data.model.InvoicesModel
import com.neqabty.healthcare.core.data.PreferencesHelper
import javax.inject.Inject

class InvoicesDataSource @Inject constructor(private val invoicesApi: InvoicesApi, private val preferencesHelper: PreferencesHelper) {

    suspend fun getAllInvoices(): List<InvoicesModel>{
        return invoicesApi.getAllInvoices(token = "Token ${preferencesHelper.token}")
    }
}