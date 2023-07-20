package com.neqabty.healthcare.onboarding.contact.domain.interactors

import com.neqabty.healthcare.onboarding.contact.data.model.SubmitClientRequest
import com.neqabty.healthcare.onboarding.contact.domain.entity.SubmitClientEntity
import com.neqabty.healthcare.onboarding.contact.domain.repository.ContactRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SubmitClientUseCase @Inject constructor(private val repository: ContactRepository) {
    fun build(submitClientRequest: SubmitClientRequest): Flow<SubmitClientEntity> {
        return repository.submitClient(submitClientRequest)
    }
}