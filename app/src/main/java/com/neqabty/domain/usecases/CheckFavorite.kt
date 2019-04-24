package com.neqabty.domain.usecases

import com.neqabty.domain.NeqabtyCache
import com.neqabty.domain.common.Transformer
import com.neqabty.domain.entities.ProviderEntity
import io.reactivex.Observable
import javax.inject.Inject

class CheckFavorite @Inject constructor(
    transformer: Transformer<Boolean>,
    private val neqabtyCache: NeqabtyCache
) : UseCase<Boolean>(transformer) {

    companion object {
        private const val PARAM_ENTITY = "param:entity"
    }

    fun CheckFavorite(providerEntity: ProviderEntity): Observable<Boolean> {
        val data = HashMap<String, ProviderEntity>()
        data[CheckFavorite.PARAM_ENTITY] = providerEntity
        return observable(data)
    }

    override fun createObservable(data: Map<String, Any>?): Observable<Boolean> {
        val providerEntity = data?.get(CheckFavorite.PARAM_ENTITY) as ProviderEntity
        return neqabtyCache.checkFavorite(providerEntity)
    }
}