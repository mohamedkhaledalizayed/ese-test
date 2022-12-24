package com.neqabty.recruitment.modules.engineer.domain.repository

import com.neqabty.recruitment.modules.personalinfo.data.model.EngineerBody
import com.neqabty.recruitment.modules.engineer.domain.entity.engineerdata.EngineerEntity
import kotlinx.coroutines.flow.Flow

interface EngineerRepository {
    fun getEngineerData(): Flow<EngineerEntity>
    fun updateEngineerData(id: String, engineerBody: EngineerBody): Flow<EngineerEntity>
}