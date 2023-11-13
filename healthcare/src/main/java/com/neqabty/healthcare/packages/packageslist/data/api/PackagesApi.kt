package com.neqabty.healthcare.packages.packageslist.data.api

import com.neqabty.healthcare.packages.packageslist.data.model.InsuranceBody
import com.neqabty.healthcare.packages.packageslist.data.model.PackagesListModel
import com.neqabty.healthcare.packages.packageslist.data.model.insurance.InsuranceModelList
import retrofit2.http.*

interface PackagesApi {

    @GET("healthcare/api/v1/packages")
    suspend fun getPackages(@Header("Authorization") token: String, @Query("entity_code") code: String): PackagesListModel

    @POST("healthcare/api/v1/insurance-policy")
    suspend fun getInsuranceDocs(@Header("Authorization") token: String, @Body insuranceBody: InsuranceBody): InsuranceModelList

}