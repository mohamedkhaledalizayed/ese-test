package com.neqabty.healthcare.commen.onboarding.contact.domain.interactors

import com.neqabty.healthcare.commen.onboarding.contact.domain.entity.CheckMemberEntity
import com.neqabty.healthcare.commen.onboarding.contact.domain.repository.ContactRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CheckMemberUseCase @Inject constructor(private val repository: ContactRepository) {
    fun build(nationalId: String): Flow<CheckMemberEntity> {
        return repository.checkMember(nationalId)
    }
}