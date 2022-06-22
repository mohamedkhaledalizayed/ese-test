package com.neqabty.healthcare.modules.search.data.api

import com.neqabty.healthcare.modules.search.data.model.MedicalProvidersResponse
import com.neqabty.healthcare.modules.search.data.model.Response
import com.neqabty.healthcare.modules.search.data.model.SearchBody
import com.neqabty.healthcare.modules.search.data.model.filter.FiltersListModel
import com.neqabty.healthcare.modules.search.data.model.packages.PackagesListModel
import com.neqabty.healthcare.modules.search.data.model.search.ProvidersResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface SearchApi {

    @GET("medicalProviders")
    suspend fun getMedicalProviders(): Response<MedicalProvidersResponse>

    @POST("medicalProviders/search")
    suspend fun searchMedicalProviders(@Body body: SearchBody): ProvidersResponse

    @GET("medicalProviders/getLockups")
    suspend fun getFilters(): FiltersListModel

    @GET("packages")
    suspend fun getPackages(): PackagesListModel

}