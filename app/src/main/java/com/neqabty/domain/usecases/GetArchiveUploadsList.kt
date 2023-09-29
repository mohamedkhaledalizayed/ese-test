package com.neqabty.domain.usecases

import com.neqabty.domain.NeqabtyRepository
import com.neqabty.domain.common.Transformer
import com.neqabty.domain.entities.ArchiveUploadItemEntity
import io.reactivex.Observable
import javax.inject.Inject

class GetArchiveUploadsList @Inject constructor(
    transformer: Transformer<List<ArchiveUploadItemEntity>>,
    private val neqabtyRepository: NeqabtyRepository
) : UseCase<List<ArchiveUploadItemEntity>>(transformer) {

    companion object {
        private const val PARAM_USER_NUMBER = "param:userNumer"
        private const val PARAM_CAT_ID = "param:catId"
    }

    fun getArchiveUploadsList(userNumber: String, categoryId: Int): Observable<List<ArchiveUploadItemEntity>> {
        val data = HashMap<String, Any>()
        data[PARAM_USER_NUMBER] = userNumber
        data[PARAM_CAT_ID] = categoryId
        return observable(data)
    }

    override fun createObservable(data: Map<String, Any>?): Observable<List<ArchiveUploadItemEntity>> {
        val userNumber = data?.get(PARAM_USER_NUMBER) as String
        val categoryId = data?.get(PARAM_CAT_ID) as Int
        return neqabtyRepository.getArchiveUploads(userNumber, categoryId)
    }
}