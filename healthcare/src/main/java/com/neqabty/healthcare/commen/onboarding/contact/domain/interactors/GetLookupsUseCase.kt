package com.neqabty.healthcare.commen.onboarding.contact.domain.interactors

import com.neqabty.healthcare.commen.onboarding.contact.domain.entity.GovEntity
import com.neqabty.healthcare.commen.onboarding.contact.domain.repository.ContactRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLookupsUseCase @Inject constructor(private val repository: ContactRepository) {
    fun build(): Flow<List<GovEntity>> {
        return repository.getLookups()
    }
}