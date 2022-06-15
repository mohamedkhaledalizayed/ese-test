package com.neqabty.healthcare.modules.home.data.api

import com.neqabty.healthcare.modules.home.data.model.about.AboutListModel
import retrofit2.http.GET

interface HomeApi {
    @GET("neqabtyInfo")
    suspend fun getAboutList(): AboutListModel
}