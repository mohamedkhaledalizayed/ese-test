package com.neqabty.healthcare.medicalnetwork.data.api

import com.neqabty.healthcare.medicalnetwork.data.model.*
import com.neqabty.healthcare.medicalnetwork.data.model.area.AreaListModel
import com.neqabty.healthcare.medicalnetwork.data.model.filter.FiltersListModel
import com.neqabty.healthcare.medicalnetwork.data.model.search.ProvidersModel
import com.neqabty.healthcare.medicalnetwork.data.model.search.ProvidersResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface SearchApi {

    @GET("healthcare/api/v1/medicalProviders/{id}")
    suspend fun getMedicalProviderDetails(@Path("id") id: String): Response<ProvidersModel>

    @GET("healthcare/api/v1/medicalProviders")
    suspend fun getMedicalProviders(): Response<MedicalProvidersResponse>

    @POST("healthcare/api/v1/medicalProviders/search")
    suspend fun searchMedicalProviders(@Body body: SearchBody): ProvidersResponse

    @GET("healthcare/api/v1/medicalProviders/getLockups")
    suspend fun getFilters(): FiltersListModel

    @POST("healthcare/api/v1/medicalProviders/getAreasByGov")
    suspend fun getAreasByGov(@Body body: AreaBody): AreaListModel
}