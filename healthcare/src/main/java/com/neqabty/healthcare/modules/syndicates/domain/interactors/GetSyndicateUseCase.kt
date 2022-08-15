package com.neqabty.healthcare.modules.syndicates.domain.interactors

import com.neqabty.healthcare.modules.syndicates.domain.entity.SyndicateEntity
import com.neqabty.healthcare.modules.syndicates.domain.repository.SyndicateRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSyndicateUseCase @Inject constructor(private val syndicateRepository: SyndicateRepository) {
    fun build(): Flow<List<SyndicateEntity>> {
        return syndicateRepository.getSyndicates()
    }
}