package com.neqabty.healthcare.sustainablehealth.home.domain.repository

import com.neqabty.healthcare.sustainablehealth.home.domain.entity.AdEntity
import kotlinx.coroutines.flow.Flow

interface AdsRepository {
    fun getAllAds(): Flow<List<AdEntity>>
}