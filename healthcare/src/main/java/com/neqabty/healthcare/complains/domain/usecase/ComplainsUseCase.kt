package com.neqabty.healthcare.complains.domain.usecase

import com.neqabty.healthcare.complains.domain.entity.ComplainEntity
import com.neqabty.healthcare.complains.domain.repository.ComplainRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ComplainsUseCase @Inject constructor(private val complainRepository: ComplainRepository) {

    fun build(): Flow<List<ComplainEntity>>{
        return complainRepository.getAllComplains()
    }

}