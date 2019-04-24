package com.neqabty.domain.usecases

import com.neqabty.domain.NeqabtyRepository
import com.neqabty.domain.common.Transformer
import com.neqabty.domain.entities.SyndicateEntity
import io.reactivex.Observable
import javax.inject.Inject

class GetSubSyndicates @Inject constructor(
    transformer: Transformer<List<SyndicateEntity>>,
    private val neqabtyRepository: NeqabtyRepository
) : UseCase<List<SyndicateEntity>>(transformer) {

    companion object {
        private const val PARAM_ID = "param:id"
    }

    fun getSubSyndicateById(id: String): Observable<List<SyndicateEntity>> {
        val data = HashMap<String, String>()
        data[PARAM_ID] = id
        return observable(data)
    }

    override fun createObservable(data: Map<String, Any>?): Observable<List<SyndicateEntity>> {
        val id = data?.get(GetSubSyndicates.PARAM_ID) as String
        return neqabtyRepository.geSubSyndicatesById(id)
    }
}