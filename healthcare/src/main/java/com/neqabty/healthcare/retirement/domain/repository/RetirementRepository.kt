package com.neqabty.healthcare.retirement.domain.repository



import com.neqabty.healthcare.retirement.data.model.pension.PensionModel
import com.neqabty.healthcare.retirement.data.model.validation.ValidationModel
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface RetirementRepository {
    fun checkValidation(userNumber: String, nationalId: String): Flow<Response<ValidationModel>>
    fun getPensionInfo(id: String): Flow<Response<PensionModel>>
}