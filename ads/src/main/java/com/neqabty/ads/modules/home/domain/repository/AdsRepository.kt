package com.neqabty.ads.modules.home.domain.repository

import com.neqabty.ads.modules.home.domain.entity.AdEntity
import kotlinx.coroutines.flow.Flow

interface AdsRepository {
    fun getAllAds(): Flow<List<AdEntity>>
    fun getSyndicateAds(syndicateId:Int): Flow<List<AdEntity>>
}