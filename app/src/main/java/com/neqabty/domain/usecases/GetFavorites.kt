package com.neqabty.domain.usecases

import com.neqabty.domain.NeqabtyCache
import com.neqabty.domain.common.Transformer
import com.neqabty.domain.entities.ProviderEntity
import io.reactivex.Observable
import javax.inject.Inject

class GetFavorites @Inject constructor(
    transformer: Transformer<List<ProviderEntity>>,
    private val neqabtyCache: NeqabtyCache
) : UseCase<List<ProviderEntity>>(transformer) {

    override fun createObservable(data: Map<String, Any>?): Observable<List<ProviderEntity>> {
        return neqabtyCache.getFavorites()
    }
}