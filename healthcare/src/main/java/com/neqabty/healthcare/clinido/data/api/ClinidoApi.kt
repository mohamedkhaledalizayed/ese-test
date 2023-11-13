package com.neqabty.healthcare.clinido.data.api


import com.neqabty.healthcare.clinido.data.model.ClinidoBody
import com.neqabty.healthcare.clinido.data.model.ClinidoModel
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST


interface ClinidoApi {

    @POST("healthcare/api/v1/clinido/register")
    suspend fun register(@Header("Authorization") token: String, @Body body: ClinidoBody): ClinidoModel

}