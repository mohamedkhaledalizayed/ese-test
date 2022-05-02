package com.neqabty.domain.usecases

import com.neqabty.domain.NeqabtyRepository
import com.neqabty.domain.common.Transformer
import com.neqabty.domain.entities.ServiceTypeEntity
import io.reactivex.Observable
import javax.inject.Inject

class GetAllServiceTypes @Inject constructor(
    transformer: Transformer<ServiceTypeEntity>,
    private val neqabtyRepository: NeqabtyRepository
) : UseCase<ServiceTypeEntity>(transformer) {

    override fun createObservable(data: Map<String, Any>?): Observable<ServiceTypeEntity> {
        return neqabtyRepository.getAllServiceTypes()
    }
}