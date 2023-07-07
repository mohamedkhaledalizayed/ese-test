package com.neqabty.healthcare.commen.invoices.data.repository

import com.neqabty.healthcare.commen.invoices.data.datasource.InvoicesDataSource
import com.neqabty.healthcare.commen.invoices.data.model.InvoicesModel
import com.neqabty.healthcare.commen.invoices.domain.entity.InvoicesEntity
import com.neqabty.healthcare.commen.invoices.domain.repository.InvoicesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class InvoicesRepositoryImpl @Inject constructor(private val invoicesDataSource: InvoicesDataSource):
    InvoicesRepository {
    override fun getAllInvoices(): Flow<List<InvoicesEntity>> {
        return flow { emit(invoicesDataSource.getAllInvoices().map { it.toInvoicesEntity() }) }
    }
}

private fun InvoicesModel.toInvoicesEntity(): InvoicesEntity{
    return InvoicesEntity(
        entity = entity ?: "",
        mobile = account.mobile ?: "",
        fullName = account.fullname ?: "",
        status = status ?: "",
        membershipId = membership_id ?: "",
        createdAt = created_at ?: "",
        netAmount = net_amount ?: "",
        serviceName = service_action.name ?: "",
        totalAmount = total_amount ?: "",
        totalFees = total_fees ?: "",
        gatewayReferenceId = gateway_reference_id ?: ""
    )
}