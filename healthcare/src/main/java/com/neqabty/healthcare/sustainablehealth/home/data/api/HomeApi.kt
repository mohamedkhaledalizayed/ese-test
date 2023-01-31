package com.neqabty.healthcare.sustainablehealth.home.data.api

import com.neqabty.healthcare.sustainablehealth.home.data.model.about.AboutListModel
import com.neqabty.healthcare.sustainablehealth.home.data.model.about.packages.PackagesListModel
import retrofit2.http.GET
import retrofit2.http.Query

interface HomeApi {

    @GET("neqabtyInfo")
    suspend fun getAboutList(): AboutListModel

    @GET("packages")
    suspend fun getPackages(@Query("entity_code") code: String): PackagesListModel

}