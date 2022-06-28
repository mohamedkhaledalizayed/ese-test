package com.neqabty.meganeqabty.home.data.source


import com.neqabty.meganeqabty.home.data.api.AdsApi
import com.neqabty.meganeqabty.home.data.model.Ad
import javax.inject.Inject

class AdsDS @Inject constructor(private val adsApi: AdsApi) {
    suspend fun getAllAds(): List<Ad> {
        return adsApi.getAllAds().ads
    }
}