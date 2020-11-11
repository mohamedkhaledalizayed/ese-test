package com.neqabty.domain.usecases

import com.neqabty.domain.NeqabtyRepository
import com.neqabty.domain.common.Transformer
import com.neqabty.domain.entities.MedicalRenewalEntity
import com.neqabty.domain.entities.TripEntity
import io.reactivex.Observable
import javax.inject.Inject

class GetMedicalRenewalData @Inject constructor(
    transformer: Transformer<MedicalRenewalEntity>,
    private val neqabtyRepository: NeqabtyRepository
) : UseCase<MedicalRenewalEntity>(transformer) {

    companion object {
        private const val PARAM_ID = "param:id"
    }

    fun getMedicalRenewalData(id: String): Observable<MedicalRenewalEntity> {
        val data = HashMap<String, String>()
        data[PARAM_ID] = id
        return observable(data)
    }

    override fun createObservable(data: Map<String, Any>?): Observable<MedicalRenewalEntity> {
        val id = data?.get(PARAM_ID) as String
        return neqabtyRepository.getMedicalRenewalData(id)
    }
}