package com.neqabty.ads.modules.home.data.source

import com.neqabty.ads.modules.home.data.api.AdsApi
import com.neqabty.ads.modules.home.data.model.Ad
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AdsDS @Inject constructor(private val adsApi: AdsApi) {
    suspend fun getAllAds(): List<Ad> {
        return adsApi.getAllAds().ads
    }

    suspend fun getSyndicateAds(syndicateId:Int): List<Ad>{
        return adsApi.getSyndicateAd(syndicateId).ads
    }
}