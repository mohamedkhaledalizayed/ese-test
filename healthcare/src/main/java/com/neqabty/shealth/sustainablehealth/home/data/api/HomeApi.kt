package com.neqabty.shealth.sustainablehealth.home.data.api

import com.neqabty.shealth.sustainablehealth.home.data.model.ads.AdsResponse
import com.neqabty.shealth.sustainablehealth.home.data.model.about.AboutListModel
import retrofit2.http.GET

interface HomeApi {
    @GET("neqabtyInfo")
    suspend fun getAboutList(): AboutListModel

    @GET("announcments")
    suspend fun getAllAds(): AdsResponse

}