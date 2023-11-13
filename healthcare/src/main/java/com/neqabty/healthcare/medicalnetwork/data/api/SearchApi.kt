package com.neqabty.healthcare.medicalnetwork.data.api

import com.neqabty.healthcare.medicalnetwork.data.model.*
import com.neqabty.healthcare.medicalnetwork.data.model.area.AreaListModel
import com.neqabty.healthcare.medicalnetwork.data.model.filter.FiltersListModel
import com.neqabty.healthcare.medicalnetwork.data.model.search.ProvidersModel
import com.neqabty.healthcare.medicalnetwork.data.model.search.ProvidersResponse
import retrofit2.http.*

interface SearchApi {

    @GET("healthcare/api/v1/medicalProviders/{id}")
    suspend fun getMedicalProviderDetails(@Header("Authorization") token: String, @Path("id") id: String): Response<ProvidersModel>

    @GET("healthcare/api/v1/medicalProviders")
    suspend fun getMedicalProviders(@Header("Authorization") token: String): Response<MedicalProvidersResponse>

    @POST("healthcare/api/v1/medicalProviders/search")
    suspend fun searchMedicalProviders(@Header("Authorization") token: String, @Body body: SearchBody): ProvidersResponse

    @GET("healthcare/api/v1/medicalProviders/getLockups")
    suspend fun getFilters(@Header("Authorization") token: String): FiltersListModel

    @POST("healthcare/api/v1/medicalProviders/getAreasByGov")
    suspend fun getAreasByGov(@Header("Authorization") token: String, @Body body: AreaBody): AreaListModel
}