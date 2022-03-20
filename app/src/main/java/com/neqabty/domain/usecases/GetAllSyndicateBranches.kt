package com.neqabty.domain.usecases

import com.neqabty.domain.NeqabtyRepository
import com.neqabty.domain.common.Transformer
import com.neqabty.domain.entities.SyndicateBranchEntity
import io.reactivex.Observable
import javax.inject.Inject

class GetAllSyndicateBranches @Inject constructor(
    transformer: Transformer<List<SyndicateBranchEntity>>,
    private val neqabtyRepository: NeqabtyRepository
) : UseCase<List<SyndicateBranchEntity>>(transformer) {

    override fun createObservable(data: Map<String, Any>?): Observable<List<SyndicateBranchEntity>> {
        return neqabtyRepository.getSyndicateBranches()
    }
}