package com.neqabty.healthcare.sustainablehealth.medicalnetwork.data.api

import com.neqabty.healthcare.sustainablehealth.medicalnetwork.data.model.*
import com.neqabty.healthcare.sustainablehealth.medicalnetwork.data.model.area.AreaListModel
import com.neqabty.healthcare.sustainablehealth.medicalnetwork.data.model.filter.FiltersListModel
import com.neqabty.healthcare.sustainablehealth.medicalnetwork.data.model.search.ProvidersModel
import com.neqabty.healthcare.sustainablehealth.medicalnetwork.data.model.search.ProvidersResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface SearchApi {

    @GET("medicalProviders/{id}")
    suspend fun getMedicalProviderDetails(@Path("id") id: String): Response<ProvidersModel>

    @GET("medicalProviders")
    suspend fun getMedicalProviders(): Response<MedicalProvidersResponse>

    @POST("medicalProviders/search")
    suspend fun searchMedicalProviders(@Body body: SearchBody): ProvidersResponse

    @GET("medicalProviders/getLockups")
    suspend fun getFilters(): FiltersListModel

    @POST("medicalProviders/getAreasByGov")
    suspend fun getAreasByGov(@Body body: AreaBody): AreaListModel
}