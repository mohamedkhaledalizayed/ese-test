package com.neqabty.domain.usecases

import com.neqabty.domain.NeqabtyRepository
import com.neqabty.domain.common.Transformer
import com.neqabty.domain.entities.InquireUpdateUserDataEntity
import com.neqabty.domain.entities.UpdateUserDataEntity
import io.reactivex.Observable
import java.io.File
import javax.inject.Inject

class UpdateUserData @Inject constructor(
        transformer: Transformer<UpdateUserDataEntity>,
        private val neqabtyRepository: NeqabtyRepository
) : UseCase<UpdateUserDataEntity>(transformer) {

    companion object {
        private const val PARAM_USER_NUMBER = "param:syndicateUserNumber"
        private const val PARAM_NAME = "param:name"
        private const val PARAM_NATIONAL_ID = "param:nationalID"
        private const val PARAM_MOBILE = "param:mobile"
        private const val PARAM_DOCS_COUNT = "param:docsNumber"
        private const val PARAM_DOC1 = "param:doc1"
        private const val PARAM_DOC2 = "param:doc2"
        private const val PARAM_DOC3 = "param:doc3"
    }

    fun updateUserData(
            userNumber: String, name: String, nationalID: String, mobile: String, docsNumber: Int, doc1: File?, doc2: File?, doc3: File?
    ): Observable<UpdateUserDataEntity> {
        val data = HashMap<String, Any>()
        data[PARAM_USER_NUMBER] = userNumber
        data[PARAM_NAME] = name
        data[PARAM_NATIONAL_ID] = nationalID
        data[PARAM_MOBILE] = mobile
        data[PARAM_DOCS_COUNT] = docsNumber
        doc1?.let { data[PARAM_DOC1] = it }
        doc2?.let { data[PARAM_DOC2] = it }
        doc3?.let { data[PARAM_DOC3] = it }
        return observable(data)
    }

    override fun createObservable(data: Map<String, Any>?): Observable<UpdateUserDataEntity> {
        val userNumber = data?.get(PARAM_USER_NUMBER) as String
        val fullName = data?.get(PARAM_NAME) as String
        val nationalID = data?.get(PARAM_NATIONAL_ID) as String
        val mobile = data?.get(PARAM_MOBILE) as String
        val docsNumber = data?.get(PARAM_DOCS_COUNT) as Int
        val doc1 = data?.get(PARAM_DOC1) as File?
        val doc2 = data?.get(PARAM_DOC2) as File?
        val doc3 = data?.get(PARAM_DOC3) as File?
        return neqabtyRepository.updateUserData(userNumber, fullName, nationalID, mobile, docsNumber, doc1, doc2, doc3)
    }
}