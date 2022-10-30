package com.neqabty.shealth.sustainablehealth.search.data.api

import com.neqabty.shealth.sustainablehealth.search.data.model.MedicalProvidersResponse
import com.neqabty.shealth.sustainablehealth.search.data.model.Response
import com.neqabty.shealth.sustainablehealth.search.data.model.SearchBody
import com.neqabty.shealth.sustainablehealth.search.data.model.filter.FiltersListModel
import com.neqabty.shealth.sustainablehealth.search.data.model.packages.PackagesListModel
import com.neqabty.shealth.sustainablehealth.search.data.model.search.ProvidersResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface SearchApi {

    @GET("medicalProviders")
    suspend fun getMedicalProviders(): Response<MedicalProvidersResponse>

    @POST("medicalProviders/search")
    suspend fun searchMedicalProviders(@Body body: SearchBody): ProvidersResponse

    @GET("medicalProviders/getLockups")
    suspend fun getFilters(): FiltersListModel

    @GET("packages")
    suspend fun getPackages(@Query("entity_code") code: String): PackagesListModel

}