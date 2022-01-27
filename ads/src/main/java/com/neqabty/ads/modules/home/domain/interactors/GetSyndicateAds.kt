package com.neqabty.ads.modules.home.domain.interactors

import com.neqabty.ads.modules.home.domain.entity.AdEntity
import com.neqabty.ads.modules.home.domain.repository.AdsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSyndicateAds @Inject constructor(private val adsRepository: AdsRepository) {
    fun build(syndicateId:Int): Flow<List<AdEntity>> {
        return adsRepository.getSyndicateAds(syndicateId)
    }
}