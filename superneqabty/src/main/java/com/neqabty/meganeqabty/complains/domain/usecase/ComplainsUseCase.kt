package com.neqabty.meganeqabty.complains.domain.usecase

import com.neqabty.meganeqabty.complains.domain.repository.ComplainRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ComplainsUseCase @Inject constructor(private val complainRepository: ComplainRepository) {

    fun build(): Flow<String>{
        return complainRepository.getAllComplains()
    }

}