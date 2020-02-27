package com.neqabty.domain.usecases

import com.neqabty.domain.NeqabtyRepository
import com.neqabty.domain.common.Transformer
import com.neqabty.domain.entities.AreaEntity
import com.neqabty.domain.entities.ComplaintTypeEntity
import io.reactivex.Observable
import javax.inject.Inject

class GetComplaintTypes @Inject constructor(
    transformer: Transformer<List<ComplaintTypeEntity>>,
    private val neqabtyRepository: NeqabtyRepository
) : UseCase<List<ComplaintTypeEntity>>(transformer) {

    override fun createObservable(data: Map<String, Any>?): Observable<List<ComplaintTypeEntity>> {
        return neqabtyRepository.getComplaintTypes()
    }
}