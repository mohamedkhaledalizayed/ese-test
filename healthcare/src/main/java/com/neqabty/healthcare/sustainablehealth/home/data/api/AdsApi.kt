package com.neqabty.healthcare.sustainablehealth.home.data.api

import com.neqabty.healthcare.sustainablehealth.home.data.model.ads.AdsResponse
import com.neqabty.healthcare.sustainablehealth.home.data.model.about.AboutListModel
import retrofit2.http.GET

interface AdsApi {

    @GET("announcments")
    suspend fun getAllAds(): AdsResponse

}