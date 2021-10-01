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
    }

    fun getMedicalLetterByID(id: String): Observable<MedicalLetterEntity.LetterItem> {
        val data = HashMap<String, Any>()
        data[PARAM_ID] = id
        return observable(data)
    }

    override fun createObservable(data: Map<String, Any>?): Observable<MedicalLetterEntity.LetterItem> {
        val id = data?.get(PARAM_ID) as String
        return neqabtyRepository.getMedicalLetterByID(id)
    }
}