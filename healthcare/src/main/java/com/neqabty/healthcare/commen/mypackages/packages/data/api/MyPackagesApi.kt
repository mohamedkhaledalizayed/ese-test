package com.neqabty.healthcare.commen.mypackages.packages.data.api

import com.neqabty.healthcare.commen.mypackages.packages.data.model.PackagesBody
import com.neqabty.healthcare.commen.mypackages.packages.data.model.ProfileModel
import retrofit2.http.*

interface MyPackagesApi {

    @POST("client/profile")
    suspend fun getMyPackages(
        @Header("Authorization") token: String,
        @Body body: PackagesBody
    ): ProfileModel

}