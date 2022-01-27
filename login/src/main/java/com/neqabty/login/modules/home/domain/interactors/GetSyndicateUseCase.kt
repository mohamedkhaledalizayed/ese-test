package com.neqabty.login.modules.home.domain.interactors

import com.neqabty.login.modules.home.domain.entity.SyndicateEntity
import com.neqabty.login.modules.home.domain.repository.SyndicateRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSyndicateUseCase @Inject constructor(private val syndicateRepository: SyndicateRepository) {
    fun build(): Flow<List<SyndicateEntity>> {
        return syndicateRepository.getSyndicates()
    }
}