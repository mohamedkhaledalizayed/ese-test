package com.neqabty.domain.usecases

import com.neqabty.domain.NeqabtyRepository
import com.neqabty.domain.common.Transformer
import com.neqabty.domain.entities.HealthCareProjectStatusEntity
import io.reactivex.Observable
import javax.inject.Inject

class GetHealthCareProjectStatus @Inject constructor(
    transformer: Transformer<HealthCareProjectStatusEntity>,
    private val neqabtyRepository: NeqabtyRepository
) : UseCase<HealthCareProjectStatusEntity>(transformer) {

    override fun createObservable(data: Map<String, Any>?): Observable<HealthCareProjectStatusEntity> {
        return neqabtyRepository.getHealthCareProjectStatus()
    }
}