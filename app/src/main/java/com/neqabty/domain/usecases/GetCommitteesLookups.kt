package com.neqabty.domain.usecases

import com.neqabty.domain.NeqabtyRepository
import com.neqabty.domain.common.Transformer
import com.neqabty.domain.entities.CommitteesLookupEntity
import io.reactivex.Observable
import javax.inject.Inject

class GetCommitteesLookups @Inject constructor(
    transformer: Transformer<CommitteesLookupEntity>,
    private val neqabtyRepository: NeqabtyRepository
) : UseCase<CommitteesLookupEntity>(transformer) {

    override fun createObservable(data: Map<String, Any>?): Observable<CommitteesLookupEntity> {
        return neqabtyRepository.getCommitteesLookups()
    }
}