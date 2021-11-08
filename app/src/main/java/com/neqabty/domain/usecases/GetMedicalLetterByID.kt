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
        private const val PARAM_USER_NUMBER = "param:userNumber"
    }

    fun getMedicalLetterByID(mobileNumber: String, userNumber: String, id: String): Observable<MedicalLetterEntity.LetterItem> {
        val data = HashMap<String, Any>()
        data[PARAM_ID] = id
        data[PARAM_MOBILE_NUMBER] = mobileNumber
        data[PARAM_USER_NUMBER] = userNumber
        return observable(data)
    }

    override fun createObservable(data: Map<String, Any>?): Observable<MedicalLetterEntity.LetterItem> {
        val id = data?.get(PARAM_ID) as String
        val mobileNumber = data?.get(PARAM_MOBILE_NUMBER) as String
        val userNumber = data?.get(PARAM_USER_NUMBER) as String
        return neqabtyRepository.getMedicalLetterByID(mobileNumber, userNumber, id)
    }
}