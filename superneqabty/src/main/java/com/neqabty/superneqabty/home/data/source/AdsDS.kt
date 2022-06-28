package com.neqabty.superneqabty.home.data.source


import com.neqabty.superneqabty.home.data.api.AdsApi
import com.neqabty.superneqabty.home.data.model.Ad
import javax.inject.Inject

class AdsDS @Inject constructor(private val adsApi: AdsApi) {
    suspend fun getAllAds(): List<Ad> {
        return adsApi.getAllAds().ads
    }
}