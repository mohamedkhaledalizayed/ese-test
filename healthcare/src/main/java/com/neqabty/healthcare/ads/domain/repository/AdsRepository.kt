package com.neqabty.healthcare.ads.domain.repository

import com.neqabty.healthcare.ads.domain.entity.AdEntity
import kotlinx.coroutines.flow.Flow

interface AdsRepository {
    fun getAllAds(): Flow<List<AdEntity>>
}