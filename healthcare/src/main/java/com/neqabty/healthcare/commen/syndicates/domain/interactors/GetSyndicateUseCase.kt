package com.neqabty.healthcare.commen.syndicates.domain.interactors

import com.neqabty.healthcare.commen.syndicates.domain.entity.SyndicateEntity
import com.neqabty.healthcare.commen.syndicates.domain.repository.SyndicateRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSyndicateUseCase @Inject constructor(private val syndicateRepository: SyndicateRepository) {
    fun build(): Flow<List<SyndicateEntity>> {
        return syndicateRepository.getSyndicates()
    }
}