package com.neqabty.domain.usecases

import com.neqabty.domain.NeqabtyRepository
import com.neqabty.domain.common.Transformer
import com.neqabty.domain.entities.FawryTransactionEntity
import io.reactivex.Observable
import javax.inject.Inject

class CreateFawryTransaction @Inject constructor(
    transformer: Transformer<FawryTransactionEntity>,
    private val neqabtyRepository: NeqabtyRepository
) : UseCase<FawryTransactionEntity>(transformer) {

    companion object {
        private const val PARAM_REF_ID = "param:referenceID"
    }

    fun createFawryTransaction(refrenceId: String): Observable<FawryTransactionEntity> {
        val data = HashMap<String, String>()
        data[PARAM_REF_ID] = refrenceId
        return observable(data)
    }

    override fun createObservable(data: Map<String, Any>?): Observable<FawryTransactionEntity> {
        val refrenceId = data?.get(PARAM_REF_ID) as String
        return neqabtyRepository.createFawryTransaction(refrenceId)
    }
}