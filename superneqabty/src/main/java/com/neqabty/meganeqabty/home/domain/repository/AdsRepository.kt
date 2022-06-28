package com.neqabty.meganeqabty.home.domain.repository

import com.neqabty.meganeqabty.home.domain.entity.AdEntity
import kotlinx.coroutines.flow.Flow

interface AdsRepository {
    fun getAllAds(): Flow<List<AdEntity>>
}