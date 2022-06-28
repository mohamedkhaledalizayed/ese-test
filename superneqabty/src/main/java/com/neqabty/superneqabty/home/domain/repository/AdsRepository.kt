package com.neqabty.superneqabty.home.domain.repository

import com.neqabty.superneqabty.home.domain.entity.AdEntity
import kotlinx.coroutines.flow.Flow

interface AdsRepository {
    fun getAllAds(): Flow<List<AdEntity>>
}