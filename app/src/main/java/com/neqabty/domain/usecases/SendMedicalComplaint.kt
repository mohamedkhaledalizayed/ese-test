package com.neqabty.domain.usecases

import com.neqabty.domain.NeqabtyRepository
import com.neqabty.domain.common.Transformer
import com.neqabty.domain.entities.AttachmentEntity
import com.neqabty.domain.entities.MedicalComplaintEntity
import io.reactivex.Observable
import javax.inject.Inject

class SendMedicalComplaint @Inject constructor(
        transformer: Transformer<MedicalComplaintEntity>,
        private val neqabtyRepository: NeqabtyRepository
) : UseCase<MedicalComplaintEntity>(transformer) {

    companion object {
        private const val PARAM_NAME = "param:name"
        private const val PARAM_MOBILE = "param:mobile"
        private const val PARAM_USER_NUMBER = "param:userNumber"
        private const val PARAM_BEN_ID = "param:benId"
        private const val PARAM_DESCRIPTION = "param:description"
        private const val PARAM_BRANCH_PROFILE_ID = "param:branchProfileId"
        private const val PARAM_SERVICE_PROVIDER_ID = "param:serviceProviderId"
        private const val PARAM_LETTER_TYPE_ID = "param:letterTypeId"
        private const val PARAM_ATTACHMENTS = "param:attachments"
    }

    fun sendMedicalComplaint(
        name: String,
        mobile: String,
        userNumber: String,
        benId: String,
        description: String,
        branchProfileId: String,
        serviceProviderId: String,
        letterTypeId: String,
        attachments: List<AttachmentEntity>
    ): Observable<MedicalComplaintEntity> {
        val data = HashMap<String, Any>()
        data[PARAM_NAME] = name
        data[PARAM_MOBILE] = mobile
        data[PARAM_USER_NUMBER] = userNumber
        data[PARAM_BEN_ID] = benId
        data[PARAM_DESCRIPTION] = description
        data[PARAM_BRANCH_PROFILE_ID] = branchProfileId
        data[PARAM_SERVICE_PROVIDER_ID] = serviceProviderId
        data[PARAM_LETTER_TYPE_ID] = letterTypeId
        data[PARAM_ATTACHMENTS] = attachments
        return observable(data)
    }

    override fun createObservable(data: Map<String, Any>?): Observable<MedicalComplaintEntity> {
        val name = data?.get(PARAM_NAME) as String
        val mobile = data?.get(PARAM_MOBILE) as String
        val userNumber = data?.get(PARAM_USER_NUMBER) as String
        val benId = data?.get(PARAM_BEN_ID) as String
        val description = data?.get(PARAM_DESCRIPTION) as String
        val branchProfileId = data?.get(PARAM_BRANCH_PROFILE_ID) as String
        val serviceProviderId = data?.get(PARAM_SERVICE_PROVIDER_ID) as String
        val letterTypeId = data?.get(PARAM_LETTER_TYPE_ID) as String
        val attachments = data?.get(PARAM_ATTACHMENTS) as List<AttachmentEntity>
        return neqabtyRepository.sendMedicalComplaint(name, mobile, userNumber, benId, description, branchProfileId, serviceProviderId, letterTypeId, attachments)
    }
}