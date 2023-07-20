package com.neqabty.healthcare.invoices.domain.repository

import com.neqabty.healthcare.invoices.domain.entity.InvoicesEntity
import kotlinx.coroutines.flow.Flow

interface InvoicesRepository {
    fun getAllInvoices(): Flow<List<InvoicesEntity>>
}