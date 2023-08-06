package com.neqabty.healthcare.retirement.domain.usecases


import com.neqabty.healthcare.retirement.data.model.validation.ValidationModel
import com.neqabty.healthcare.retirement.domain.repository.RetirementRepository
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject

class CheckValidationUseCase @Inject constructor(private val retirementRepository: RetirementRepository) {

    fun build(userNumber: String, nationalId: String): Flow<Response<ValidationModel>> {
        return retirementRepository.checkValidation(userNumber, nationalId)
    }

}