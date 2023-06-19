package com.neqabty.healthcare.commen.onboarding.contact.domain.interactors

import com.neqabty.healthcare.commen.onboarding.contact.domain.entity.InstallmentsEntity
import com.neqabty.healthcare.commen.onboarding.contact.domain.repository.ContactRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetInstallmentsUseCase @Inject constructor(private val repository: ContactRepository) {
    fun build(
        nationalId: String,
        amount: String,
        tenor: String
    ): Flow<InstallmentsEntity> {
        return repository.getInstallments(nationalId, amount, tenor)
    }
}