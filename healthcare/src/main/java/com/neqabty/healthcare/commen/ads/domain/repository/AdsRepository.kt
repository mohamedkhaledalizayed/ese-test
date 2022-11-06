package com.neqabty.healthcare.commen.ads.domain.repository

import com.neqabty.healthcare.commen.ads.domain.entity.AdEntity
import kotlinx.coroutines.flow.Flow

interface AdsRepository {
    fun getAllAds(): Flow<List<AdEntity>>
}