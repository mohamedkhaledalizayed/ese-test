package com.neqabty.domain.usecases

import com.neqabty.domain.NeqabtyRepository
import com.neqabty.domain.common.Transformer
import com.neqabty.domain.entities.ProviderEntity
import io.reactivex.Observable
import javax.inject.Inject

class GetAllProviders @Inject constructor(transformer: Transformer<List<ProviderEntity>>,
                                          private val neqabtyRepository: NeqabtyRepository) : UseCase<List<ProviderEntity>>(transformer) {

    companion object {
        private const val PARAM_ID = "param:id"
    }

    fun getAllProviders(id: String): Observable<List<ProviderEntity>> {
        val data = HashMap<String, String>()
        data[PARAM_ID] = id
        return observable(data)
    }

    override fun createObservable(data: Map<String, Any>?): Observable<List<ProviderEntity>> {
        val id = data?.get(GetAllProviders.PARAM_ID) as String
        return neqabtyRepository.getAllProviders(id)
    }

}