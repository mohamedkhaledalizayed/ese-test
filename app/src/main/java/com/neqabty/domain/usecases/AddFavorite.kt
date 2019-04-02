package com.neqabty.domain.usecases

import com.neqabty.domain.NeqabtyCache
import com.neqabty.domain.common.Transformer
import com.neqabty.domain.entities.ProviderEntity
import io.reactivex.Observable
import javax.inject.Inject

class AddFavorite @Inject constructor(transformer: Transformer<ProviderEntity>,
                                      private val neqabtyCache: NeqabtyCache) : UseCase<ProviderEntity>(transformer) {

    companion object {
        private const val PARAM_ENTITY = "param:entity"
    }

    fun addFavorite(providerEntity: ProviderEntity): Observable<ProviderEntity> {
        val data = HashMap<String, ProviderEntity>()
        data[AddFavorite.PARAM_ENTITY] = providerEntity
        return observable(data)
    }

    override fun createObservable(data: Map<String, Any>?): Observable<ProviderEntity> {
        val providerEntity = data?.get(AddFavorite.PARAM_ENTITY) as ProviderEntity
        return neqabtyCache.addFavorite(providerEntity)
    }

}