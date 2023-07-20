package com.neqabty.healthcare.ads.data.datasource


import com.neqabty.healthcare.ads.data.api.AdsApi
import com.neqabty.healthcare.ads.data.model.Ad
import com.neqabty.healthcare.core.data.PreferencesHelper
import javax.inject.Inject

class AdsDS @Inject constructor(private val adsApi: AdsApi, private val preferencesHelper: PreferencesHelper) {

    suspend fun getAllAds(): List<Ad> {
        return adsApi.getAllAds().ads
    }

}