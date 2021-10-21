package com.neqabty.domain.usecases

import com.neqabty.domain.NeqabtyRepository
import com.neqabty.domain.common.Transformer
import com.neqabty.domain.entities.MedicalLetterEntity
import io.reactivex.Observable
import javax.inject.Inject

class GetMedicalLetterByID @Inject constructor(
    transformer: Transformer<MedicalLetterEntity.LetterItem>,
    private val neqabtyRepository: NeqabtyRepository
) : UseCase<MedicalLetterEntity.LetterItem>(transformer) {

    companion object {
        private const val PARAM_ID = "param:ID"
        private const val PARAM_MOBILE_NUMBER = "param:mobileNumber"
    }

    fun getMedicalLetterByID(mobileNumber: String, id: String): Observable<MedicalLetterEntity.LetterItem> {
        val data = HashMap<String, Any>()
        data[PARAM_ID] = id
        data[PARAM_MOBILE_NUMBER] = mobileNumber
        return observable(data)
    }

    override fun createObservable(data: Map<String, Any>?): Observable<MedicalLetterEntity.LetterItem> {
        val id = data?.get(PARAM_ID) as String
        val mobileNumber = data?.get(PARAM_MOBILE_NUMBER) as String
        return neqabtyRepository.getMedicalLetterByID(mobileNumber, id)
    }
}