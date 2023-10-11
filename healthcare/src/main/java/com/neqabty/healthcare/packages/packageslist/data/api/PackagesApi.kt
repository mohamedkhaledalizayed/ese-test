package com.neqabty.healthcare.packages.packageslist.data.api

import com.neqabty.healthcare.packages.packageslist.data.model.PackagesListModel
import retrofit2.http.GET
import retrofit2.http.Query

interface PackagesApi {

    @GET("healthcare/api/v1/packages")
    suspend fun getPackages(@Query("entity_code") code: String): PackagesListModel

}