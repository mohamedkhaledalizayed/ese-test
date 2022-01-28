package com.neqabty.login.modules.home.domain.repository

import com.neqabty.login.modules.home.domain.entity.SyndicateEntity
import kotlinx.coroutines.flow.Flow

interface SyndicateRepository {
    fun getSyndicates(): Flow<List<SyndicateEntity>>
}