package com.neqabty.domain.usecases

import com.neqabty.domain.NeqabtyRepository
import com.neqabty.domain.common.Transformer
import io.reactivex.Observable
import java.io.File
import javax.inject.Inject

class SendEngineeringRecordsRequest @Inject constructor(
    transformer: Transformer<Unit>,
    private val neqabtyRepository: NeqabtyRepository
) : UseCase<Unit>(transformer) {

    companion object {
        private const val PARAM_NAME = "param:name"
        private const val PARAM_PHONE = "param:phone"
        private const val PARAM_TYPE_ID = "param:typeID"
        private const val PARAM_MAIN_SYNDICATE = "param:mainSyndicate"
        private const val PARAM_USER_NUMBER = "param:syndicateUserNumber"
        private const val PARAM_RENEWAL_DATE = "param:renewalDate"
        private const val PARAM_STATUS_ID = "param:statusID"
        private const val PARAM_IS_OWNER = "param:isOwner"
        private const val PARAM_DOCS_COUNT = "param:docsNumber"
        private const val PARAM_DOC1 = "param:doc1"
    }

    fun requestEngineeringRecords(
            name: String,
            phone: String,
            typeId: String,
            mainSyndicate: String,
            userNumber: String,
            lastRenewYear: String,
            statusID: Int,
            isOwner: Int,
            docsNumber: Int,
            doc1: File?
    ): Observable<Unit> {
        val data = HashMap<String, Any>()
        data[PARAM_NAME] = name
        data[PARAM_PHONE] = phone
        data[PARAM_TYPE_ID] = typeId
        data[PARAM_MAIN_SYNDICATE] = mainSyndicate
        data[PARAM_USER_NUMBER] = userNumber
        data[PARAM_RENEWAL_DATE] = lastRenewYear
        data[PARAM_STATUS_ID] = statusID
        data[PARAM_IS_OWNER] = isOwner
        data[PARAM_DOCS_COUNT] = docsNumber
        doc1?.let { data[PARAM_DOC1] = it }
        return observable(data)
    }

    override fun createObservable(data: Map<String, Any>?): Observable<Unit> {
        val name = data?.get(PARAM_NAME) as String
        val phone = data?.get(PARAM_PHONE) as String
        val typeID = data?.get(PARAM_TYPE_ID) as String
        val mainSyndicateId = data?.get(PARAM_MAIN_SYNDICATE) as String
        val userNumber = data?.get(PARAM_USER_NUMBER) as String
        val renewalYear = data?.get(PARAM_RENEWAL_DATE) as String
        val statusID = data?.get(PARAM_STATUS_ID) as Int
        val isOwner = data?.get(PARAM_IS_OWNER) as Int
        val docsNumber = data?.get(PARAM_DOCS_COUNT) as Int
        val doc1 = data?.get(PARAM_DOC1) as File?
        return neqabtyRepository.requestEngineeringRecords(name, phone,typeID,mainSyndicateId,userNumber, renewalYear, statusID, isOwner,docsNumber , doc1)
    }
}