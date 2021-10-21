package com.neqabty.domain.usecases

import com.neqabty.domain.NeqabtyRepository
import com.neqabty.domain.common.Transformer
import com.neqabty.domain.entities.MedicalLetterEntity
import io.reactivex.Observable
import javax.inject.Inject

class GetMedicalLetters @Inject constructor(
    transformer: Transformer<MedicalLetterEntity>,
    private val neqabtyRepository: NeqabtyRepository
) : UseCase<MedicalLetterEntity>(transformer) {

    companion object {
        private const val PARAM_BEN_ID = "param:benID"
        private const val PARAM_START = "param:start"
        private const val PARAM_END = "param:end"
        private const val PARAM_ORDER_BY = "param:orderBy"
        private const val PARAM_DIR = "param:dir"
        private const val PARAM_MOBILE_NUMBER = "param:mobileNumber"
    }

    fun getMedicalLetters(benID: String, start: Int, end: Int, orderBy: String, dir: String, mobileNumber: String): Observable<MedicalLetterEntity> {
        val data = HashMap<String, Any>()
        data[PARAM_BEN_ID] = benID
        data[PARAM_START] = start
        data[PARAM_END] = end
        data[PARAM_ORDER_BY] = orderBy
        data[PARAM_DIR] = dir
        data[PARAM_MOBILE_NUMBER] = mobileNumber
        return observable(data)
    }

    override fun createObservable(data: Map<String, Any>?): Observable<MedicalLetterEntity> {
        val benID = data?.get(PARAM_BEN_ID) as String
        val start = data?.get(PARAM_START) as Int
        val end = data?.get(PARAM_END) as Int
        val orderBy = data?.get(PARAM_ORDER_BY) as String
        val dir = data?.get(PARAM_DIR) as String
        val mobileNumber = data?.get(PARAM_MOBILE_NUMBER) as String
        return neqabtyRepository.getMedicalLetters(mobileNumber, benID, start,end,orderBy,dir)
    }
}