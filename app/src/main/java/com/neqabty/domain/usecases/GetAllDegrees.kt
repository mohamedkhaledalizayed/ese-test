package com.neqabty.domain.usecases

import com.neqabty.domain.NeqabtyRepository
import com.neqabty.domain.common.Transformer
import com.neqabty.domain.entities.DegreeEntity
import com.neqabty.domain.entities.SpecializationEntity
import io.reactivex.Observable
import javax.inject.Inject

class GetAllDegrees @Inject constructor(transformer: Transformer<List<DegreeEntity>>,
                                        private val neqabtyRepository: NeqabtyRepository) : UseCase<List<DegreeEntity>>(transformer) {

    override fun createObservable(data: Map<String, Any>?): Observable<List<DegreeEntity>> {
        return neqabtyRepository.getAllDegrees()
    }

}