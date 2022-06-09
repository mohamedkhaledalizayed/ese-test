package com.neqabty.healthcare.modules.home.data.api

import com.neqabty.healthcare.modules.home.data.model.MedicalProvidersResponse
import com.neqabty.healthcare.modules.home.data.model.Response
import retrofit2.http.GET

interface HealthCareApi {
    @GET("medicalProviders")
    suspend fun getMedicalProviders(): Response<MedicalProvidersResponse>
}