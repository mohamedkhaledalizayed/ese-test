package com.neqabty.recruitment.modules.personalinfo.data.datasource

import com.neqabty.recruitment.modules.personalinfo.data.api.PersonalInfo
import com.neqabty.recruitment.modules.personalinfo.data.model.EngineerBody
import com.neqabty.recruitment.modules.personalinfo.data.model.country.CountryModel
import com.neqabty.recruitment.modules.personalinfo.data.model.country.CountryModelList
import com.neqabty.recruitment.modules.personalinfo.data.model.engineerdata.EngineerModel
import com.neqabty.recruitment.modules.personalinfo.data.model.governement.GovernorateModel
import com.neqabty.recruitment.modules.personalinfo.data.model.maritalstatus.MaritalStatusModel
import com.neqabty.recruitment.modules.personalinfo.data.model.nationalities.NationalityModel
import com.neqabty.recruitment.modules.personalinfo.data.model.universities.UniversityModel
import javax.inject.Inject

class PersonalInfoDS @Inject constructor(private val personalInfo: PersonalInfo) {

    suspend fun getEngineerData(): EngineerModel {
        return personalInfo.getEngineerData("3608662").engineer
    }

    suspend fun updateEngineerData(id: String, engineerBody: EngineerBody): EngineerModel {
        return personalInfo.updateEngineerData(id, engineerBody)
    }

    suspend fun getMaritalStatus(): List<MaritalStatusModel>{
        return personalInfo.getMaritalStatus().maritalstatuses
    }

    suspend fun getNationalities(): List<NationalityModel>{
        return personalInfo.getNationalities().nationalities
    }

    suspend fun getCountries(): List<CountryModel>{
        return personalInfo.getCountries().cities
    }

    suspend fun getGovernorates(): List<GovernorateModel>{
        return personalInfo.getGovernorates().governorates
    }

    suspend fun getUniversities(): List<UniversityModel>{
        return personalInfo.getUniversities().universities
    }

}