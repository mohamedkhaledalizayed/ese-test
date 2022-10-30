package com.neqabty.healthcare.sustainablehealth.home.data.source


import com.neqabty.healthcare.sustainablehealth.home.data.api.AdsApi
import com.neqabty.healthcare.sustainablehealth.home.data.model.ads.Ad
import javax.inject.Inject

class AdsDS @Inject constructor(private val adsApi: AdsApi) {
    suspend fun getAllAds(): List<Ad> {
        return adsApi.getAllAds().ads
    }
}