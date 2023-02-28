package com.neqabty.healthcare.commen.clinido.data.api


import com.neqabty.healthcare.commen.clinido.data.model.ClinidoBody
import com.neqabty.healthcare.commen.clinido.data.model.ClinidoModel
import retrofit2.http.Body
import retrofit2.http.POST


interface ClinidoApi {

    @POST("clinido/register")
    suspend fun register(@Body body: ClinidoBody): ClinidoModel

}