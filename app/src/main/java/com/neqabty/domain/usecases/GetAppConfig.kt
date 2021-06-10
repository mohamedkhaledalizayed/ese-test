package com.neqabty.domain.usecases

import com.neqabty.domain.NeqabtyRepository
import com.neqabty.domain.common.Transformer
import com.neqabty.domain.entities.AppConfigEntity
import io.reactivex.Observable
import javax.inject.Inject

class GetAppConfig @Inject constructor(
        transformer: Transformer<AppConfigEntity>,
        private val neqabtyRepository: NeqabtyRepository
) : UseCase<AppConfigEntity>(transformer) {

    override fun createObservable(data: Map<String, Any>?): Observable<AppConfigEntity> {
        return neqabtyRepository.getAppConfig()
    }
}