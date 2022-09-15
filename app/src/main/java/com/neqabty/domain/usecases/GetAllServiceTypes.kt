package com.neqabty.domain.usecases

import com.neqabty.domain.NeqabtyRepository
import com.neqabty.domain.common.Transformer
import com.neqabty.domain.entities.ServiceEntity
import com.neqabty.domain.entities.ServiceTypeEntity
import io.reactivex.Observable
import javax.inject.Inject

class GetAllServiceTypes @Inject constructor(
    transformer: Transformer<List<ServiceTypeEntity>>,
    private val neqabtyRepository: NeqabtyRepository
) : UseCase<List<ServiceTypeEntity>>(transformer) {

    override fun createObservable(data: Map<String, Any>?): Observable<List<ServiceTypeEntity>> {
        return neqabtyRepository.getAllServiceTypes()
    }
}