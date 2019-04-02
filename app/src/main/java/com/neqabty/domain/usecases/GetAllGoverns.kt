package com.neqabty.domain.usecases

import com.neqabty.domain.NeqabtyRepository
import com.neqabty.domain.common.Transformer
import com.neqabty.domain.entities.GovernEntity
import io.reactivex.Observable
import javax.inject.Inject

class GetAllGoverns @Inject constructor(transformer: Transformer<List<GovernEntity>>,
                                        private val neqabtyRepository: NeqabtyRepository) : UseCase<List<GovernEntity>>(transformer) {

    override fun createObservable(data: Map<String, Any>?): Observable<List<GovernEntity>> {
        return neqabtyRepository.getAllGoverns()
    }

}