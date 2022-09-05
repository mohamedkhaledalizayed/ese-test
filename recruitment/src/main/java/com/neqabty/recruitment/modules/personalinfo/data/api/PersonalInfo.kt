package com.neqabty.recruitment.modules.personalinfo.data.api

import com.neqabty.recruitment.modules.personalinfo.data.model.EngineerBody
import com.neqabty.recruitment.modules.personalinfo.data.model.engineerdata.EngineerModel
import com.neqabty.recruitment.modules.personalinfo.data.model.maritalstatus.MaritalStatusModelList
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path

interface PersonalInfo {

    //Engineer

    @GET("engineer/engineers/{id}")
    suspend fun getEngineerData(@Path("id") id: String): EngineerModel

    @PATCH("engineer/engineers/{id}")
    suspend fun updateEngineerData(@Path("id") id: String, @Body engineerBody: EngineerBody): EngineerModel

    @GET("templatefields/maritalstatus")
    suspend fun getMaritalStatus(): MaritalStatusModelList
}