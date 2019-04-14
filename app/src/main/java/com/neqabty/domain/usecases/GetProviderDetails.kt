package com.neqabty.domain.usecases

import com.neqabty.domain.NeqabtyRepository
import com.neqabty.domain.common.Transformer
import com.neqabty.domain.entities.ProviderEntity
import io.reactivex.Observable
import javax.inject.Inject

class GetProviderDetails @Inject constructor(transformer: Transformer<ProviderEntity>,
                                             private val neqabtyRepository: NeqabtyRepository) : UseCase<ProviderEntity>(transformer) {

    companion object {
        private const val PARAM_ID = "param:id"
        private const val PARAM_TYPE = "param:type"
    }

    fun getProviderDetails(id: String,type :String): Observable<ProviderEntity> {
        val data = HashMap<String, String>()
        data[PARAM_ID] = id
        data[PARAM_TYPE] = type
        return observable(data)
    }

    override fun createObservable(data: Map<String, Any>?): Observable<ProviderEntity> {
        val id = data?.get(GetProviderDetails.PARAM_ID) as String
        val type = data?.get(GetProviderDetails.PARAM_TYPE) as String
        return neqabtyRepository.getProviderDetails(id,type)
    }

}