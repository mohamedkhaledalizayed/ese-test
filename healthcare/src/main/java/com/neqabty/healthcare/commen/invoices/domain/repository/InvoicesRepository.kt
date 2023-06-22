package com.neqabty.healthcare.commen.invoices.domain.repository

import com.neqabty.healthcare.commen.invoices.domain.entity.InvoicesEntity
import kotlinx.coroutines.flow.Flow

interface InvoicesRepository {
    fun getAllInvoices(): Flow<List<InvoicesEntity>>
}