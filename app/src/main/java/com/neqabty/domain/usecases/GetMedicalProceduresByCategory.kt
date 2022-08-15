package com.neqabty.domain.usecases

import com.neqabty.domain.NeqabtyRepository
import com.neqabty.domain.common.Transformer
import com.neqabty.domain.entities.MedicalProcedureEntity
import io.reactivex.Observable
import javax.inject.Inject

class GetMedicalProceduresByCategory @Inject constructor(
    transformer: Transformer<List<MedicalProcedureEntity>>,
    private val neqabtyRepository: NeqabtyRepository
) : UseCase<List<MedicalProcedureEntity>>(transformer) {

    companion object {
        private const val PARAM_MOBILE = "param:mobile"
        private const val PARAM_CATEGORY_ID = "param:categoryId"
    }

    fun getProcedures(
        mobileNumber: String,
        categoryId: String
    ): Observable<List<MedicalProcedureEntity>> {
        val data = HashMap<String, String>()
        data[PARAM_MOBILE] = mobileNumber
        data[PARAM_CATEGORY_ID] = categoryId
        return observable(data)
    }

    override fun createObservable(data: Map<String, Any>?): Observable<List<MedicalProcedureEntity>> {
        val mobileNumber = data?.get(PARAM_MOBILE) as String
        val categoryId = data?.get(PARAM_CATEGORY_ID) as String
        return neqabtyRepository.getMedicalProcedures(mobileNumber, categoryId)
    }
}