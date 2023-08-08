package com.neqabty.healthcare.retirement.domain.usecases


import com.neqabty.healthcare.retirement.domain.repository.RetirementRepository
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject

class GetInheritorUseCase @Inject constructor(private val retirementRepository: RetirementRepository) {

    fun build(id: String): Flow<Response<String>> {
        return retirementRepository.getInheritor(id)
    }

}