package com.neqabty.domain.usecases

import com.neqabty.domain.NeqabtyRepository
import com.neqabty.domain.common.Transformer
import io.reactivex.Observable
import java.io.File
import javax.inject.Inject

class SendMedicalRequest @Inject constructor(
    transformer: Transformer<Unit>,
    private val neqabtyRepository: NeqabtyRepository
) : UseCase<Unit>(transformer) {

    companion object {
        private const val PARAM_MAIN_SYNDICATE = "param:mainSyndicate"
        private const val PARAM_SUB_SYNDICATE = "param:subSyndicate"
        private const val PARAM_USER_NUMBER = "param:syndicateUserNumber"
        private const val PARAM_EMAIL = "param:email"
        private const val PARAM_PHONE = "param:phone"
        private const val PARAM_PROFESSION = "param:profession"
        private const val PARAM_DEGREE = "param:degree"
        private const val PARAM_AREA = "param:area"
        private const val PARAM_DOCTOR = "param:doctor"
        private const val PARAM_PROVIDER_TYPE = "param:providerType"
        private const val PARAM_PROVIDER = "param:provider"
        private const val PARAM_NAME = "param:name"
        private const val PARAM_DOCS_COUNT = "param:docsNumber"
        private const val PARAM_DOC1 = "param:doc1"
        private const val PARAM_DOC2 = "param:doc2"
        private const val PARAM_DOC3 = "param:doc3"
        private const val PARAM_DOC4 = "param:doc4"
        private const val PARAM_DOC5 = "param:doc5"
    }

    fun sendMedicalRequest(
        mainSyndicateId: Int,
        subSyndicateId: Int,
        userNumber: String,
        email: String,
        phone: String,
        profession: Int,
        degree: Int,
        area: Int,
        doctor: Int,
        providerType: Int,
        provider: Int,
        name: String,
        docsNumber: Int,
        doc1: File?,
        doc2: File?,
        doc3: File?,
        doc4: File?,
        doc5: File?
    ): Observable<Unit> {
        val data = HashMap<String, Any>()
        data[PARAM_MAIN_SYNDICATE] = mainSyndicateId
        data[PARAM_SUB_SYNDICATE] = subSyndicateId
        data[PARAM_USER_NUMBER] = userNumber
        data[PARAM_EMAIL] = email
        data[PARAM_PHONE] = phone
        data[PARAM_PROFESSION] = profession
        data[PARAM_DEGREE] = degree
        data[PARAM_AREA] = area
        data[PARAM_DOCTOR] = doctor
        data[PARAM_PROVIDER_TYPE] = providerType
        data[PARAM_PROVIDER] = provider
        data[PARAM_NAME] = name
        data[PARAM_DOCS_COUNT] = docsNumber
        doc1?.let { data[PARAM_DOC1] = it }
        doc2?.let { data[PARAM_DOC2] = it }
        doc3?.let { data[PARAM_DOC3] = it }
        doc4?.let { data[PARAM_DOC4] = it }
        doc5?.let { data[PARAM_DOC5] = it }
        return observable(data)
    }

    override fun createObservable(data: Map<String, Any>?): Observable<Unit> {
        val mainSyndicateId = data?.get(SendMedicalRequest.PARAM_MAIN_SYNDICATE) as Int
        val subSyndicateId = data?.get(SendMedicalRequest.PARAM_SUB_SYNDICATE) as Int
        val userNumber = data?.get(SendMedicalRequest.PARAM_USER_NUMBER) as String
        val email = data?.get(SendMedicalRequest.PARAM_EMAIL) as String
        val phone = data?.get(SendMedicalRequest.PARAM_PHONE) as String
        val profession = data?.get(SendMedicalRequest.PARAM_PROFESSION) as Int
        val degree = data?.get(SendMedicalRequest.PARAM_DEGREE) as Int
        val area = data?.get(SendMedicalRequest.PARAM_AREA) as Int
        val doctor = data?.get(SendMedicalRequest.PARAM_DOCTOR) as Int
        val docsNumber = data?.get(SendMedicalRequest.PARAM_DOCS_COUNT) as Int
        val providerType = data?.get(SendMedicalRequest.PARAM_PROVIDER_TYPE) as Int
        val provider = data?.get(SendMedicalRequest.PARAM_PROVIDER) as Int
        val name = data?.get(SendMedicalRequest.PARAM_NAME) as String
        val doc1 = data?.get(SendMedicalRequest.PARAM_DOC1) as File?
        val doc2 = data?.get(SendMedicalRequest.PARAM_DOC2) as File?
        val doc3 = data?.get(SendMedicalRequest.PARAM_DOC3) as File?
        val doc4 = data?.get(SendMedicalRequest.PARAM_DOC4) as File?
        val doc5 = data?.get(SendMedicalRequest.PARAM_DOC5) as File?
        return neqabtyRepository.sendMedicalRequest(mainSyndicateId, subSyndicateId, userNumber, email, phone, profession, degree, area, doctor, providerType, provider, name , docsNumber, doc1, doc2, doc3, doc4, doc5)
    }
}