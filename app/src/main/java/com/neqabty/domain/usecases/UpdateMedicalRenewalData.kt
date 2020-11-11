package com.neqabty.domain.usecases

import com.neqabty.domain.NeqabtyRepository
import com.neqabty.domain.common.Transformer
import com.neqabty.domain.entities.MedicalRenewalEntity
import com.neqabty.domain.entities.MedicalRenewalPaymentEntity
import com.neqabty.domain.entities.MedicalRenewalUpdateEntity
import io.reactivex.Observable
import javax.inject.Inject

class UpdateMedicalRenewalData @Inject constructor(
        transformer: Transformer<MedicalRenewalUpdateEntity>,
        private val neqabtyRepository: NeqabtyRepository
) : UseCase<MedicalRenewalUpdateEntity>(transformer) {

    companion object {
        private const val PARAM_ENTITY = "param:entity"
    }

    fun updateMedicalRenewalData(medicalRenewalData: MedicalRenewalEntity): Observable<MedicalRenewalUpdateEntity> {
        val data = HashMap<String, Any>()
        data[PARAM_ENTITY] = medicalRenewalData
        return observable(data)
    }

    override fun createObservable(data: Map<String, Any>?): Observable<MedicalRenewalUpdateEntity> {
        val medicalRenewalData = data?.get(PARAM_ENTITY) as MedicalRenewalEntity
        return neqabtyRepository.updateMedicalRenewalData(medicalRenewalData)
    }
}