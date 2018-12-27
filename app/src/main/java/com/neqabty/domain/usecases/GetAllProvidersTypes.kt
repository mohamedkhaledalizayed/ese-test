package com.neqabty.domain.usecases

import com.neqabty.domain.NeqabtyRepository
import com.neqabty.domain.common.Transformer
import com.neqabty.domain.entities.ProviderTypeEntitiy
import io.reactivex.Observable
import javax.inject.Inject

class GetAllProvidersTypes @Inject constructor(transformer: Transformer<List<ProviderTypeEntitiy>>,
                                               private val neqabtyRepository: NeqabtyRepository) : UseCase<List<ProviderTypeEntitiy>>(transformer) {

    override fun createObservable(data: Map<String, Any>?): Observable<List<ProviderTypeEntitiy>> {
        return neqabtyRepository.getAllProviderTypes()
    }

}