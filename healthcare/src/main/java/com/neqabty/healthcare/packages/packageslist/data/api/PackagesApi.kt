package com.neqabty.healthcare.packages.packageslist.data.api

import com.neqabty.healthcare.packages.packageslist.data.model.InsuranceBody
import com.neqabty.healthcare.packages.packageslist.data.model.PackagesListModel
import com.neqabty.healthcare.packages.packageslist.data.model.insurance.InsuranceModelList
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface PackagesApi {

    @GET("healthcare/api/v1/packages")
    suspend fun getPackages(@Query("entity_code") code: String): PackagesListModel

    @POST("healthcare/api/v1/insurance-policy")
    suspend fun getInsuranceDocs(@Body insuranceBody: InsuranceBody): InsuranceModelList

}