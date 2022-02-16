package com.neqabty.superneqabty.syndicates.domain.repository

import com.neqabty.superneqabty.syndicates.domain.entity.SyndicateEntity
import kotlinx.coroutines.flow.Flow

interface SyndicateRepository {
    fun getSyndicates(): Flow<List<SyndicateEntity>>
}