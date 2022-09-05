package com.neqabty.recruitment.modules.personalinfo.data.datasource

import com.neqabty.recruitment.modules.personalinfo.data.api.PersonalInfo
import com.neqabty.recruitment.modules.personalinfo.data.model.EngineerBody
import com.neqabty.recruitment.modules.personalinfo.data.model.engineerdata.EngineerModel
import com.neqabty.recruitment.modules.personalinfo.data.model.maritalstatus.MaritalStatusModel
import javax.inject.Inject

class PersonalInfoDS @Inject constructor(private val personalInfo: PersonalInfo) {

    suspend fun getEngineerData(): EngineerModel {
        return personalInfo.getEngineerData("3608662")
    }

    suspend fun updateEngineerData(id: String, engineerBody: EngineerBody): EngineerModel {
        return personalInfo.updateEngineerData(id, engineerBody)
    }

    suspend fun getMaritalStatus(): List<MaritalStatusModel>{
        return personalInfo.getMaritalStatus().maritalstatuses
    }

}