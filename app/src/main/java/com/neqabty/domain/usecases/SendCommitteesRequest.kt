package com.neqabty.domain.usecases

import com.neqabty.domain.NeqabtyRepository
import com.neqabty.domain.common.Transformer
import io.reactivex.Observable
import java.io.File
import javax.inject.Inject

class SendCommitteesRequest @Inject constructor(
    transformer: Transformer<String>,
    private val neqabtyRepository: NeqabtyRepository
) : UseCase<String>(transformer) {

    companion object {
        private const val PARAM_NAME = "param:name"
        private const val PARAM_USER_NUMBER = "param:userNumber"
        private const val PARAM_MOBILE = "param:mobile"
        private const val PARAM_EMAIL = "param:email"
        private const val PARAM_NATIONAL_ID = "param:nationalId"
        private const val PARAM_ADDRESS = "param:address"
        private const val PARAM_UNI = "param:university"
        private const val PARAM_DEGREE = "param:degree"
        private const val PARAM_STATUS = "param:maritalStatus"
        private const val PARAM_COMMITTEES = "param:committeesIds"
        private const val PARAM_SECTION_ID = "param:sectionId"
        private const val PARAM_SYNDICATE_ID = "param:syndicateId"
        private const val PARAM_DEPARTMENT = "param:department"
        private const val PARAM_SECTION = "param:section"
        private const val PARAM_JOB = "param:currentJob"
        private const val PARAM_DETAILS = "param:details"
        private const val PARAM_FOLLOWER_NAME = "param:followerName"
        private const val PARAM_FOLLOWER_RELATION = "param:followerRelation"
        private const val PARAM_DOCS_COUNT = "param:docsNumber"
        private const val PARAM_DOC1 = "param:doc1"
        private const val PARAM_DOC2 = "param:doc2"
        private const val PARAM_DOC3 = "param:doc3"
    }

    fun sendCommitteesRequest(
        name: String,
        user_number: String,
        mobile: String,
        email: String,
        nationalId: String,
        address: String,
        university: String,
        degree: String,
        maritalStatus: String,
        committeesIds: List<Int>,
        sectionId: Int,
        syndicateId: Int,
        department: String,
        section: String,
        currentJob: String,
        details: String,
        docsNumber: Int,
        doc1: File?,
        doc2: File?,
        doc3: File?
    ): Observable<String> {
        val data = HashMap<String, Any>()
        data[PARAM_NAME] = name
        data[PARAM_USER_NUMBER] = user_number
        data[PARAM_MOBILE] = mobile
        data[PARAM_EMAIL] = email
        data[PARAM_NATIONAL_ID] = nationalId
        data[PARAM_ADDRESS] = address
        data[PARAM_UNI] = university
        data[PARAM_DEGREE] = degree
        data[PARAM_STATUS] = maritalStatus
        data[PARAM_COMMITTEES] = committeesIds
        data[PARAM_SECTION_ID] = sectionId
        data[PARAM_SYNDICATE_ID] = syndicateId
        data[PARAM_DEPARTMENT] = department
        data[PARAM_SECTION] = section
        data[PARAM_JOB] = currentJob
        data[PARAM_DETAILS] = details
        data[PARAM_DOCS_COUNT] = docsNumber
        doc1?.let { data[PARAM_DOC1] = it }
        doc2?.let { data[PARAM_DOC2] = it }
        doc3?.let { data[PARAM_DOC3] = it }
        return observable(data)
    }

    override fun createObservable(data: Map<String, Any>?): Observable<String> {
        val name = data?.get(PARAM_NAME) as String
        val userNumber = data?.get(PARAM_USER_NUMBER) as String
        val mobile = data?.get(PARAM_MOBILE) as String
        val email = data?.get(PARAM_EMAIL) as String
        val nationalId = data?.get(PARAM_NATIONAL_ID) as String
        val address = data?.get(PARAM_ADDRESS) as String
        val university = data?.get(PARAM_UNI) as String
        val degree = data?.get(PARAM_DEGREE) as String
        val maritalStatus = data?.get(PARAM_STATUS) as String
        val committeesIds = data?.get(PARAM_COMMITTEES) as List<Int>
        val sectionId = data?.get(PARAM_SECTION_ID) as Int
        val syndicateId = data?.get(PARAM_SYNDICATE_ID) as Int
        val department = data?.get(PARAM_DEPARTMENT) as String
        val section = data?.get(PARAM_SECTION) as String
        val currentJob = data?.get(PARAM_JOB) as String
        val details = data?.get(PARAM_DETAILS) as String
        val docsNumber = data?.get(PARAM_DOCS_COUNT) as Int
        val doc1 = data?.get(PARAM_DOC1) as File?
        val doc2 = data?.get(PARAM_DOC2) as File?
        val doc3 = data?.get(PARAM_DOC3) as File?
        return neqabtyRepository.sendCommitteesRequest(name, userNumber, mobile, email, nationalId, address, university, degree, maritalStatus, committeesIds, sectionId, syndicateId, department, section, currentJob, details, docsNumber, doc1, doc2, doc3)
    }
}