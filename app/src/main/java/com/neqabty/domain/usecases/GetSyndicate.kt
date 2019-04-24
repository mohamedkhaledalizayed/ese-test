package com.neqabty.domain.usecases

import com.neqabty.domain.NeqabtyRepository
import com.neqabty.domain.common.Transformer
import com.neqabty.domain.entities.SyndicateEntity
import io.reactivex.Observable
import javax.inject.Inject

class GetSyndicate @Inject constructor(
    transformer: Transformer<SyndicateEntity>,
    private val neqabtyRepository: NeqabtyRepository
) : UseCase<SyndicateEntity>(transformer) {

    companion object {
        private const val PARAM_ID = "param:id"
    }

    fun getSyndicateById(id: String): Observable<SyndicateEntity> {
        val data = HashMap<String, String>()
        data[PARAM_ID] = id
        return observable(data)
    }

    override fun createObservable(data: Map<String, Any>?): Observable<SyndicateEntity> {
        val id = data?.get(GetSyndicate.PARAM_ID) as String
        return neqabtyRepository.geSyndicateById(id)
    }
}