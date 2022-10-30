package com.neqabty.shealth.sustainablehealth.home.domain.repository

import com.neqabty.shealth.sustainablehealth.home.domain.entity.AdEntity
import kotlinx.coroutines.flow.Flow

interface AdsRepository {
    fun getAllAds(): Flow<List<AdEntity>>
}