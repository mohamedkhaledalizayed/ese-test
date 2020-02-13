package com.neqabty.domain.usecases

import com.neqabty.domain.NeqabtyRepository
import com.neqabty.domain.common.Transformer
import com.neqabty.domain.entities.AppVersionEntity
import io.reactivex.Observable
import javax.inject.Inject

class GetAppVersion @Inject constructor(
    transformer: Transformer<AppVersionEntity>,
    private val neqabtyRepository: NeqabtyRepository
) : UseCase<AppVersionEntity>(transformer) {

    override fun createObservable(data: Map<String, Any>?): Observable<AppVersionEntity> {
        return neqabtyRepository.getAppVersion()
    }
}