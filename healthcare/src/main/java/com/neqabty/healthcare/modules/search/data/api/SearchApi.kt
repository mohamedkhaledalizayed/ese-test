package com.neqabty.healthcare.modules.search.data.api

import com.neqabty.healthcare.modules.search.data.model.MedicalProvidersResponse
import com.neqabty.healthcare.modules.search.data.model.Response
import retrofit2.http.GET

interface SearchApi {
    @GET("medicalProviders")
    suspend fun getMedicalProviders(): Response<MedicalProvidersResponse>
}