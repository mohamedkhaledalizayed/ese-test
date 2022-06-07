package com.neqabty.domain.usecases

import com.neqabty.domain.NeqabtyRepository
import com.neqabty.domain.common.Transformer
import com.neqabty.domain.entities.MedicalBranchProcedureEntity
import io.reactivex.Observable
import javax.inject.Inject

class GetMedicalBranchProcedures @Inject constructor(
    transformer: Transformer<List<MedicalBranchProcedureEntity>>,
    private val neqabtyRepository: NeqabtyRepository
) : UseCase<List<MedicalBranchProcedureEntity>>(transformer) {

    companion object {
        private const val PARAM_MOBILE = "param:mobile"
        private const val PARAM_PROCEDURE_ID = "param:procedureId"
        private const val PARAM_RELATION_TYPE_ID = "param:relationTypeId"
        private const val PARAM_AREA_ID = "param:areaId"
    }

    fun getProcedures(mobileNumber: String, procedureId: String, relationTypeId: String, areaId: String): Observable<List<MedicalBranchProcedureEntity>> {
        val data = HashMap<String, String>()
        data[PARAM_MOBILE] = mobileNumber
        data[PARAM_PROCEDURE_ID] = procedureId
        data[PARAM_RELATION_TYPE_ID] = relationTypeId
        data[PARAM_AREA_ID] = areaId
        return observable(data)
    }

    override fun createObservable(data: Map<String, Any>?): Observable<List<MedicalBranchProcedureEntity>> {
        val mobileNumber = data?.get(PARAM_MOBILE) as String
        val procedureId = data?.get(PARAM_PROCEDURE_ID) as String
        val relationTypeId = data?.get(PARAM_RELATION_TYPE_ID) as String
        val areaId = data?.get(PARAM_AREA_ID) as String
        return neqabtyRepository.getMedicalBranchProcedures(mobileNumber, procedureId, relationTypeId, areaId)
    }
}