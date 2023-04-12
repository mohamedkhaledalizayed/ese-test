package com.neqabty.healthcare.mega.payment.domain.interactors

import com.neqabty.healthcare.mega.payment.domain.entity.serviceactions.ServiceActionsEntity
import com.neqabty.healthcare.mega.payment.domain.repository.PaymentRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetServiceActionsUseCase @Inject constructor(private val repository: PaymentRepository) {

    fun getServiceActions(code: String): Flow<List<ServiceActionsEntity>> {
        return repository.getServiceActions(code)
    }

}