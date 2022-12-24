package com.neqabty.recruitment.modules.engineer.domain.usecases

import com.neqabty.recruitment.modules.engineer.domain.entity.engineerdata.EngineerEntity
import com.neqabty.recruitment.modules.engineer.domain.repository.EngineerRepository
import com.neqabty.recruitment.modules.personalinfo.data.model.EngineerBody
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UpdateEngineer @Inject constructor(private val engineerRepository: EngineerRepository) {

    fun build(id: String, engineerBody: EngineerBody): Flow<EngineerEntity> {
        return engineerRepository.updateEngineerData(id, engineerBody)
    }

}