package com.neqabty.recruitment.modules.personalinfo.data.api

import com.neqabty.recruitment.modules.personalinfo.data.model.country.CountryModelList
import com.neqabty.recruitment.modules.personalinfo.data.model.governement.GovernmentListModel
import com.neqabty.recruitment.modules.personalinfo.data.model.maritalstatus.MaritalStatusModelList
import com.neqabty.recruitment.modules.personalinfo.data.model.nationalities.NationalitiesModelList
import com.neqabty.recruitment.modules.personalinfo.data.model.universities.UniversityLisModel
import retrofit2.http.GET

interface PersonalInfo {

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