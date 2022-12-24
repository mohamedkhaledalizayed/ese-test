package com.neqabty.recruitment.modules.engineer.domain.usecases

import com.neqabty.recruitment.modules.engineer.domain.entity.engineerdata.EngineerEntity
import com.neqabty.recruitment.modules.engineer.domain.repository.EngineerRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetEngineer @Inject constructor(private val engineerRepository: EngineerRepository) {

    fun build(): Flow<EngineerEntity> {
        return engineerRepository.getEngineerData()
    }

}