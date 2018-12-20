package com.neqabty.domain.usecases

import com.neqabty.domain.NeqabtyRepository
import com.neqabty.domain.common.Transformer
import com.neqabty.domain.entities.AreaEntity
import com.neqabty.domain.entities.SyndicateEntity
import io.reactivex.Observable
import javax.inject.Inject

class GetAllAreas @Inject constructor(transformer: Transformer<List<AreaEntity>>,
                                      private val neqabtyRepository: NeqabtyRepository) : UseCase<List<AreaEntity>>(transformer) {

    override fun createObservable(data: Map<String, Any>?): Observable<List<AreaEntity>> {
        return neqabtyRepository.getAllAreas()
    }

}