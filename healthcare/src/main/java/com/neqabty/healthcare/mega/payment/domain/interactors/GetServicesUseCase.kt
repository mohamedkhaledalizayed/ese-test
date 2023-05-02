package com.neqabty.healthcare.mega.payment.domain.interactors

import com.neqabty.healthcare.mega.payment.domain.entity.services.ServicesListEntity
import com.neqabty.healthcare.mega.payment.domain.repository.PaymentRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetServicesUseCase @Inject constructor(private val repository: PaymentRepository) {

    fun getServices(): Flow<List<ServicesListEntity>> {
        return repository.getServices()
    }

}