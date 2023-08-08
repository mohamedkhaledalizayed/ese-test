package com.neqabty.healthcare.retirement.data.repository



import com.neqabty.healthcare.retirement.data.datasource.RetirementDS
import com.neqabty.healthcare.retirement.data.model.pension.PensionModel
import com.neqabty.healthcare.retirement.data.model.validation.ValidationModel
import com.neqabty.healthcare.retirement.domain.repository.RetirementRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject


class RetirementRepositoryImpl @Inject constructor(private val retirementDS: RetirementDS) :
    RetirementRepository {

    override fun checkValidation(userNumber: String, nationalId: String): Flow<Response<ValidationModel>> {
        return flow {
            emit(retirementDS.checkValidation(userNumber, nationalId))
        }
    }

    override fun getPensionInfo(id: String): Flow<Response<PensionModel>> {
        return flow {
            emit(retirementDS.getPensionInfo(id))
        }
    }

    override fun getInheritor(id: String): Flow<Response<String>> {
        return flow {
            emit(retirementDS.getInheritor(id))
        }
    }

}