package com.neqabty.domain.usecases

import com.neqabty.domain.NeqabtyRepository
import com.neqabty.domain.common.Transformer
import com.neqabty.domain.entities.SyndicateServicesEntity
import io.reactivex.Observable
import javax.inject.Inject

class GetSyndicateServices @Inject constructor(
    transformer: Transformer<SyndicateServicesEntity>,
    private val neqabtyRepository: NeqabtyRepository
) : UseCase<SyndicateServicesEntity>(transformer) {

    companion object {
        private const val PARAM_USER_NUMBER = "param:userNumber"
    }

    fun getSyndicateServices(userNumber: String): Observable<SyndicateServicesEntity> {
        val data = HashMap<String, Any>()
        data[PARAM_USER_NUMBER] = userNumber
        return observable(data)
    }

    override fun createObservable(data: Map<String, Any>?): Observable<SyndicateServicesEntity> {
        val userNumber = data?.get(PARAM_USER_NUMBER) as String
        return neqabtyRepository.getSyndicateServices(userNumber)
    }
}