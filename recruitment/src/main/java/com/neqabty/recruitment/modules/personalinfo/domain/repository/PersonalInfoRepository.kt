package com.neqabty.recruitment.modules.personalinfo.domain.repository

import com.neqabty.recruitment.modules.personalinfo.data.model.EngineerBody
import com.neqabty.recruitment.modules.personalinfo.domain.entity.engineerdata.EngineerEntity
import com.neqabty.recruitment.modules.personalinfo.domain.entity.maritalstatus.MaritalStatusEntity
import kotlinx.coroutines.flow.Flow

interface PersonalInfoRepository {

    fun getEngineerData(): Flow<EngineerEntity>
    fun updateEngineerData(id: String, engineerBody: EngineerBody): Flow<EngineerEntity>
    fun getMaritalStatus(): Flow<List<MaritalStatusEntity>>

}