package com.neqabty.healthcare.commen.invoices.domain.usecases

import com.neqabty.healthcare.commen.invoices.domain.entity.InvoicesEntity
import com.neqabty.healthcare.commen.invoices.domain.repository.InvoicesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllInvoicesUseCase @Inject constructor(private val invoicesRepository: InvoicesRepository) {
    fun build(): Flow<List<InvoicesEntity>>{
        return invoicesRepository.getAllInvoices()
    }
}