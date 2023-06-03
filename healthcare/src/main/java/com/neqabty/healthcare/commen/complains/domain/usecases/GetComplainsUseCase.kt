package com.neqabty.healthcare.commen.complains.domain.usecases

import com.neqabty.healthcare.commen.complains.domain.entity.getcomplain.ComplainEntity
import com.neqabty.healthcare.commen.complains.domain.repository.ComplainRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetComplainsUseCase @Inject constructor(private val complainRepository: ComplainRepository) {

    fun build(): Flow<List<ComplainEntity>>{
        return complainRepository.getAllComplains()
    }

}