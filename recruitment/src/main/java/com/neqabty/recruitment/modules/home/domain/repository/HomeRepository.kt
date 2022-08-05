package com.neqabty.recruitment.modules.home.domain.repository

import com.neqabty.recruitment.modules.home.domain.entity.ads.AdEntity
import kotlinx.coroutines.flow.Flow

interface HomeRepository {
    fun getAds(): Flow<List<AdEntity>>
}