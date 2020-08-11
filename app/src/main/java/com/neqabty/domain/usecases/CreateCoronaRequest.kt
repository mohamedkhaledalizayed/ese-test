package com.neqabty.domain.usecases

import com.neqabty.domain.NeqabtyRepository
import com.neqabty.domain.common.Transformer
import io.reactivex.Observable
import java.io.File
import javax.inject.Inject

class CreateCoronaRequest @Inject constructor(
    transformer: Transformer<Unit>,
    private val neqabtyRepository: NeqabtyRepository
) : UseCase<Unit>(transformer) {

    companion object {
        private const val PARAM_USER_NUMBER = "param:syndicateUserNumber"
        private const val PARAM_PHONE = "param:phone"
        private const val PARAM_SYNDICATE_ID = "param:syndicateID"
        private const val PARAM_NAME = "param:name"
        private const val PARAM_TYPE = "param:type"
        private const val PARAM_JOB = "param:job"
        private const val PARAM_WORK = "param:work"
        private const val PARAM_TREATMENT_DESTINATION = "param:treatmentDestination"
        private const val PARAM_TREATMENT_DESTINATION_ADDRESS = "param:treatmentDestinationAddress"
        private const val PARAM_FAMILY = "param:family"
        private const val PARAM_INJURY = "param:injury"
        private const val PARAM_DOCS_COUNT = "param:docsNumber"
        private const val PARAM_DOC1 = "param:doc1"
        private const val PARAM_DOC2 = "param:doc2"
        private const val PARAM_DOC3 = "param:doc3"
        private const val PARAM_DOC4 = "param:doc4"
        private const val PARAM_DOC5 = "param:doc5"
    }

    fun createCoronaRequest(
        userNumber: String,
        phone: String,
        syndicateID: Int,
        name: String,
        type: String,
        job: String,
        work: String,
        treatmentDestination: String,
        treatmentDestinationAddress: String,
        family: Int,
        injury: String,
        docsNumber: Int,
        doc1: File?,
        doc2: File?,
        doc3: File?,
        doc4: File?,
        doc5: File?
    ): Observable<Unit> {
        val data = HashMap<String, Any>()
        data[PARAM_USER_NUMBER] = userNumber
        data[PARAM_PHONE] = phone
        data[PARAM_SYNDICATE_ID] = syndicateID
        data[PARAM_NAME] = name
        data[PARAM_TYPE] = type
        data[PARAM_JOB] = job
        data[PARAM_WORK] = work
        data[PARAM_TREATMENT_DESTINATION] = treatmentDestination
        data[PARAM_TREATMENT_DESTINATION_ADDRESS] = treatmentDestinationAddress
        data[PARAM_FAMILY] = family
        data[PARAM_INJURY] = injury
        data[PARAM_DOCS_COUNT] = docsNumber
        doc1?.let { data[PARAM_DOC1] = it }
        doc2?.let { data[PARAM_DOC2] = it }
        doc3?.let { data[PARAM_DOC3] = it }
        doc4?.let { data[PARAM_DOC4] = it }
        doc5?.let { data[PARAM_DOC5] = it }
        return observable(data)
    }

    override fun createObservable(data: Map<String, Any>?): Observable<Unit> {
        val userNumber = data?.get(PARAM_USER_NUMBER) as String
        val phone = data?.get(PARAM_PHONE) as String
        val syndicateID = data?.get(PARAM_SYNDICATE_ID) as Int
        val name = data?.get(PARAM_NAME) as String
        val type = data?.get(PARAM_TYPE) as String
        val job = data?.get(PARAM_JOB) as String
        val work = data?.get(PARAM_WORK) as String
        val treatmentDestination = data?.get(PARAM_TREATMENT_DESTINATION) as String
        val treatmentDestinationAddress = data?.get(PARAM_TREATMENT_DESTINATION_ADDRESS) as String
        val family = data?.get(PARAM_FAMILY) as Int
        val injury = data?.get(PARAM_INJURY) as String
        val docsNumber = data?.get(PARAM_DOCS_COUNT) as Int
        val doc1 = data?.get(PARAM_DOC1) as File?
        val doc2 = data?.get(PARAM_DOC2) as File?
        val doc3 = data?.get(PARAM_DOC3) as File?
        val doc4 = data?.get(PARAM_DOC4) as File?
        val doc5 = data?.get(PARAM_DOC5) as File?
        return neqabtyRepository.createCoronaRequest(userNumber, phone, syndicateID, name, type, job, work, treatmentDestination, treatmentDestinationAddress, family, injury, docsNumber, doc1, doc2, doc3, doc4, doc5)
    }
}