package com.neqabty.domain.usecases

import com.neqabty.domain.NeqabtyRepository
import com.neqabty.domain.common.Transformer
import com.neqabty.domain.entities.ArchiveUploadAcknowledgementEntity
import io.reactivex.Observable
import java.io.File
import javax.inject.Inject

class UploadToArchive @Inject constructor(
    transformer: Transformer<ArchiveUploadAcknowledgementEntity>,
    private val neqabtyRepository: NeqabtyRepository
) : UseCase<ArchiveUploadAcknowledgementEntity>(transformer) {

    companion object {
        private const val PARAM_USER_NUMBER = "param:syndicateUserNumber"
        private const val PARAM_NAME = "param:name"
        private const val PARAM_DESCRIPTION = "param:description"
        private const val PARAM_CAT_ID = "param:catID"
        private const val PARAM_DOCS_COUNT = "param:docsNumber"
        private const val PARAM_DOC1 = "param:doc1"
    }

    fun uploadToArchive(
        userNumber: String,
        name: String,
        description: String,
        catId: String,
        docsNumber: Int,
        doc1: File?
    ): Observable<ArchiveUploadAcknowledgementEntity> {
        val data = HashMap<String, Any>()
        data[PARAM_USER_NUMBER] = userNumber
        data[PARAM_NAME] = name
        data[PARAM_DESCRIPTION] = description
        data[PARAM_CAT_ID] = catId
        data[PARAM_DOCS_COUNT] = docsNumber
        doc1?.let { data[PARAM_DOC1] = it }
        return observable(data)
    }

    override fun createObservable(data: Map<String, Any>?): Observable<ArchiveUploadAcknowledgementEntity> {
        val userNumber = data?.get(PARAM_USER_NUMBER) as String
        val name = data?.get(PARAM_NAME) as String
        val description = data?.get(PARAM_DESCRIPTION) as String
        val catId = data?.get(PARAM_CAT_ID) as String
        val docsNumber = data?.get(PARAM_DOCS_COUNT) as Int
        val doc1 = data?.get(PARAM_DOC1) as File?
        return neqabtyRepository.uploadToArchive(userNumber, name, description, catId, docsNumber, doc1)
    }
}