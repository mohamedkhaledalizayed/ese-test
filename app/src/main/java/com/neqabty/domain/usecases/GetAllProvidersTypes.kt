package com.neqabty.domain.usecases

import com.neqabty.domain.NeqabtyRepository
import com.neqabty.domain.common.Transformer
import com.neqabty.domain.entities.ProviderTypeEntitiy
import io.reactivex.Observable
import javax.inject.Inject

class GetAllProvidersTypes @Inject constructor(transformer: Transformer<List<ProviderTypeEntitiy>>,
                                               private val neqabtyRepository: NeqabtyRepository) : UseCase<List<ProviderTypeEntitiy>>(transformer) {

    companion object {
        private const val PARAM_PROVIDER_TYPE = "param:providerType"
    }

    fun getProviderTypes(type: String): Observable<List<ProviderTypeEntitiy>> {
        val data = HashMap<String, String>()
        data[PARAM_PROVIDER_TYPE] = type
        return observable(data)
    }


    override fun createObservable(data: Map<String, Any>?): Observable<List<ProviderTypeEntitiy>> {
        val type = data?.get(GetAllProvidersTypes.PARAM_PROVIDER_TYPE) as String
        return neqabtyRepository.getAllProviderTypes(type)
    }

}