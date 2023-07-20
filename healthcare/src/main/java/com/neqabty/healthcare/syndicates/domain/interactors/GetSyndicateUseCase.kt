package com.neqabty.healthcare.syndicates.domain.interactors

import com.neqabty.healthcare.syndicates.domain.entity.SyndicateEntity
import com.neqabty.healthcare.syndicates.domain.repository.SyndicateRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSyndicateUseCase @Inject constructor(private val syndicateRepository: SyndicateRepository) {
    fun build(): Flow<List<SyndicateEntity>> {
        return syndicateRepository.getSyndicates()
    }
}