package com.neqabty.domain.usecases

import com.neqabty.domain.NeqabtyRepository
import com.neqabty.domain.common.Transformer
import com.neqabty.domain.entities.AreaEntity
import com.neqabty.domain.entities.ServiceEntity
import io.reactivex.Observable
import javax.inject.Inject

class GetAllServices @Inject constructor(
    transformer: Transformer<List<ServiceEntity>>,
    private val neqabtyRepository: NeqabtyRepository
) : UseCase<List<ServiceEntity>>(transformer) {

    override fun createObservable(data: Map<String, Any>?): Observable<List<ServiceEntity>> {
        return neqabtyRepository.getAllServices()
    }
}