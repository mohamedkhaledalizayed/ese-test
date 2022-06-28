package com.neqabty.meganeqabty.syndicates.domain.repository

import com.neqabty.meganeqabty.syndicates.domain.entity.SyndicateEntity
import kotlinx.coroutines.flow.Flow

interface SyndicateRepository {
    fun getSyndicates(): Flow<List<SyndicateEntity>>
}