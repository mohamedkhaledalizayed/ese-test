package com.neqabty.healthcare.commen.complains.domain.usecases



import com.neqabty.healthcare.commen.complains.domain.repository.ComplainRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AddComplainUseCase @Inject constructor(private val complainRepository: ComplainRepository) {
    fun build(mobile: String, email: String, message: String): Flow<String> {
        return complainRepository.addComplain(mobile, email, message)
    }
}