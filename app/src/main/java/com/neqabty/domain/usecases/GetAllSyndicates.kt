package com.neqabty.domain.usecases

import com.neqabty.domain.NeqabtyRepository
import com.neqabty.domain.common.Transformer
import com.neqabty.domain.entities.SyndicateEntity
import io.reactivex.Observable
import javax.inject.Inject

class GetAllSyndicates @Inject constructor(transformer: Transformer<List<SyndicateEntity>>,
                                           private val neqabtyRepository: NeqabtyRepository) : UseCase<List<SyndicateEntity>>(transformer) {

    override fun createObservable(data: Map<String, Any>?): Observable<List<SyndicateEntity>> {
        return neqabtyRepository.getSyndicates()
    }

}