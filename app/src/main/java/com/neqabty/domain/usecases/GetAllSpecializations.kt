package com.neqabty.domain.usecases

import com.neqabty.domain.NeqabtyRepository
import com.neqabty.domain.common.Transformer
import com.neqabty.domain.entities.SpecializationEntity
import io.reactivex.Observable
import javax.inject.Inject

class GetAllSpecializations @Inject constructor(
    transformer: Transformer<List<SpecializationEntity>>,
    private val neqabtyRepository: NeqabtyRepository
) : UseCase<List<SpecializationEntity>>(transformer) {

    override fun createObservable(data: Map<String, Any>?): Observable<List<SpecializationEntity>> {
        return neqabtyRepository.getAllSpecializations()
    }
}