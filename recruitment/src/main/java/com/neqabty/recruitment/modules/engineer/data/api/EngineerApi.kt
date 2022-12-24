package com.neqabty.recruitment.modules.engineer.data.api

import com.neqabty.recruitment.modules.personalinfo.data.model.EngineerBody
import com.neqabty.recruitment.modules.engineer.data.model.EngineerResponse
import com.neqabty.recruitment.modules.engineer.data.model.engineerdata.EngineerModel
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path

interface EngineerApi {

    @GET("engineer/engineers/{id}")
    suspend fun getEngineerData(@Path("id") id: String): EngineerResponse

    @PATCH("engineer/engineers/{id}")
    suspend fun updateEngineerData(@Path("id") id: String, @Body engineerBody: EngineerBody): EngineerModel


}