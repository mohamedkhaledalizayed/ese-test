package com.neqabty.healthcare.commen.packages.packageslist.data.api

import com.neqabty.healthcare.commen.packages.packageslist.data.model.PackagesListModel
import retrofit2.http.GET
import retrofit2.http.Query

interface PackagesApi {

    @GET("packages")
    suspend fun getPackages(@Query("entity_code") code: String): PackagesListModel

}