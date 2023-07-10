package com.neqabty.healthcare.commen.onboarding.contact.domain.interactors

import com.neqabty.healthcare.commen.onboarding.contact.domain.entity.InvoiceEntity
import com.neqabty.healthcare.commen.onboarding.contact.domain.repository.ContactRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SubmitInvoiceUseCase @Inject constructor(private val repository: ContactRepository) {
    fun build(
        nationalId: String,
        amount: String,
        tenor: String
    ): Flow<InvoiceEntity> {
        return repository.submitInvoice(nationalId, amount, tenor)
    }
}