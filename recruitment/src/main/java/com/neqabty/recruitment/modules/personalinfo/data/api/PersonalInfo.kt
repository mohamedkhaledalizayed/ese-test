package com.neqabty.recruitment.modules.personalinfo.data.api

import com.neqabty.recruitment.modules.personalinfo.data.model.EngineerBody
import com.neqabty.recruitment.modules.personalinfo.data.model.EngineerResponse
import com.neqabty.recruitment.modules.personalinfo.data.model.country.CountryModelList
import com.neqabty.recruitment.modules.personalinfo.data.model.engineerdata.EngineerModel
import com.neqabty.recruitment.modules.personalinfo.data.model.governement.GovernmentListModel
import com.neqabty.recruitment.modules.personalinfo.data.model.maritalstatus.MaritalStatusModelList
import com.neqabty.recruitment.modules.personalinfo.data.model.nationalities.NationalitiesModelList
import com.neqabty.recruitment.modules.personalinfo.data.model.universities.UniversityLisModel
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path

interface PersonalInfo {

    //Engineer

    @GET("engineer/engineers/{id}")
    suspend fun getEngineerData(@Path("id") id: String): EngineerResponse

    @PATCH("engineer/engineers/{id}")
    suspend fun updateEngineerData(@Path("id") id: String, @Body engineerBody: EngineerBody): EngineerModel

    @GET("templatefields/maritalstatus")
    suspend fun getMaritalStatus(): MaritalStatusModelList

    @GET("templatefields/nationalities")
    suspend fun getNationalities(): NationalitiesModelList

    @GET("templatefields/cities")
    suspend fun getCountries(): CountryModelList

    @GET("templatefields/governorates")
    suspend fun getGovernorates(): GovernmentListModel

    @GET("templatefields/universities")
    suspend fun getUniversities(): UniversityLisModel
}