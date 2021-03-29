package com.neqabty.domain.usecases

import com.neqabty.domain.NeqabtyRepository
import com.neqabty.domain.common.Transformer
import io.reactivex.Observable
import java.io.File
import javax.inject.Inject

class CreateComplaint @Inject constructor(
        transformer: Transformer<Unit>,
        private val neqabtyRepository: NeqabtyRepository
) : UseCase<Unit>(transformer) {

    companion object {
        private const val PARAM_NAME = "param:name"
        private const val PARAM_MOBILE = "param:phone"
        private const val PARAM_CAT_ID = "param:catId"
        private const val PARAM_SUBCAT_ID = "param:subCatId"
        private const val PARAM_DETAILS = "param:details"
        private const val PARAM_TOKEN = "param:token"
        private const val PARAM_MEMBER_NUMBER = "param:number"
        private const val PARAM_DOCS_COUNT = "param:docsNumber"
        private const val PARAM_DOC1 = "param:doc1"
        private const val PARAM_DOC2 = "param:doc2"
        private const val PARAM_DOC3 = "param:doc3"
        private const val PARAM_DOC4 = "param:doc4"
    }

    fun createComplaint(name: String, phone: String, catId: String, subCatId: String, body: String, token: String, memberNumber: String,
                        docsNumber: Int,
                        doc1: File?,
                        doc2: File?,
                        doc3: File?,
                        doc4: File?): Observable<Unit> {
        val data = HashMap<String, Any>()
        data[PARAM_NAME] = name
        data[PARAM_MOBILE] = phone
        data[PARAM_CAT_ID] = catId
        data[PARAM_SUBCAT_ID] = subCatId
        data[PARAM_DETAILS] = body
        data[PARAM_TOKEN] = token
        data[PARAM_MEMBER_NUMBER] = memberNumber
        data[PARAM_DOCS_COUNT] = docsNumber
        doc1?.let { data[PARAM_DOC1] = it }
        doc2?.let { data[PARAM_DOC2] = it }
        doc3?.let { data[PARAM_DOC3] = it }
        doc4?.let { data[PARAM_DOC4] = it }
        return observable(data)
    }

    override fun createObservable(data: Map<String, Any>?): Observable<Unit> {
        val name = data?.get(PARAM_NAME) as String
        val phone = data?.get(PARAM_MOBILE) as String
        val catId = data?.get(PARAM_CAT_ID) as String
        val subCatId = data?.get(PARAM_SUBCAT_ID) as String
        val details = data?.get(PARAM_DETAILS) as String
        val token = data?.get(PARAM_TOKEN) as String
        val memberNumber = data?.get(PARAM_MEMBER_NUMBER) as String
        val docsNumber = data?.get(PARAM_DOCS_COUNT) as Int
        val doc1 = data?.get(PARAM_DOC1) as File?
        val doc2 = data?.get(PARAM_DOC2) as File?
        val doc3 = data?.get(PARAM_DOC3) as File?
        val doc4 = data?.get(PARAM_DOC4) as File?
        return neqabtyRepository.createComplaint(name, phone, catId, subCatId, details, token, memberNumber, docsNumber, doc1, doc2, doc3, doc4)
    }
}