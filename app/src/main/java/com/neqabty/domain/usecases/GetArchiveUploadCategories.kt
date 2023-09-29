package com.neqabty.domain.usecases

import com.neqabty.domain.NeqabtyRepository
import com.neqabty.domain.common.Transformer
import com.neqabty.domain.entities.ArchiveUploadCategoryEntity
import io.reactivex.Observable
import javax.inject.Inject

class GetArchiveUploadCategories @Inject constructor(
    transformer: Transformer<List<ArchiveUploadCategoryEntity>>,
    private val neqabtyRepository: NeqabtyRepository
) : UseCase<List<ArchiveUploadCategoryEntity>>(transformer) {

    override fun createObservable(data: Map<String, Any>?): Observable<List<ArchiveUploadCategoryEntity>> {
        return neqabtyRepository.getArchiveUploadCategories()
    }
}