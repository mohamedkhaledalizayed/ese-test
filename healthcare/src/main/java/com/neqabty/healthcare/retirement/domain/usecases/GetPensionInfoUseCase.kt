package com.neqabty.healthcare.retirement.domain.usecases



import com.neqabty.healthcare.retirement.data.model.pension.PensionModel
import com.neqabty.healthcare.retirement.domain.repository.RetirementRepository
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject

class GetPensionInfoUseCase @Inject constructor(private val retirementRepository: RetirementRepository) {

    fun build(id: String): Flow<Response<PensionModel>> {
        return retirementRepository.getPensionInfo(id)
    }

}