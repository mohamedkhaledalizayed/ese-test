package com.neqabty.healthcare.commen.onboarding.contact.domain.interactors

import com.neqabty.healthcare.commen.onboarding.contact.domain.entity.SubmitClientEntity
import com.neqabty.healthcare.commen.onboarding.contact.domain.repository.ContactRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SubmitClientUseCase @Inject constructor(private val repository: ContactRepository) {
    fun build(nationalId: String): Flow<SubmitClientEntity> {
        return repository.submitClient(nationalId)
    }
}