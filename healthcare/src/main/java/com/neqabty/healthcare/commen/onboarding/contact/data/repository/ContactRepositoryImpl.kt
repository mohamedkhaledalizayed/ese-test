package com.neqabty.healthcare.commen.onboarding.contact.data.repository


import com.neqabty.healthcare.commen.onboarding.contact.data.model.*
import com.neqabty.healthcare.commen.onboarding.contact.data.source.ContactDS
import com.neqabty.healthcare.commen.onboarding.contact.domain.entity.*
import com.neqabty.healthcare.commen.onboarding.contact.domain.repository.ContactRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MultipartBody
import javax.inject.Inject

class ContactRepositoryImpl @Inject constructor(private val contactDS: ContactDS) :
    ContactRepository {
    override fun checkMember(nationalId: String): Flow<CheckMemberEntity> {
        return flow {
            emit(contactDS.checkMember(nationalId).toMemberEntity())
        }
    }

    override fun createOCR(
        idFace: MultipartBody.Part?,
        idBack: MultipartBody.Part?,
        nationalId: String,
        mobile: String
    ): Flow<CreateOCREntity> {
        return flow {
            emit(contactDS.createOCR(idFace, idBack, nationalId, mobile).toCreateOCREntity())
        }
    }

    override fun getLookups(): Flow<List<GovEntity>> {
        return flow {
            emit(contactDS.getLookups().map { it.toGovEntity() })
        }
    }

    override fun submitClient(submitClientRequest: SubmitClientRequest): Flow<SubmitClientEntity> {
        return flow {
            emit(contactDS.submitClient(submitClientRequest).toSubmitClientEntity())
        }
    }
}

private fun CheckMemberResponse.toMemberEntity(): CheckMemberEntity {
    return CheckMemberEntity(
        authorized, ocrStatus, message
    )
}

private fun CreateOCRResponse.toCreateOCREntity(): CreateOCREntity {
    return CreateOCREntity(
        ocrs[0].idFace, ocrs[1].idBack
    )
}

private fun GovResponse.toGovEntity(): GovEntity {
    return GovEntity(
        governorateAr, areas.map { area -> AreaEntity(area.areaName) }
    )
}

private fun SubmitClientResponse.toSubmitClientEntity(): SubmitClientEntity {
    return SubmitClientEntity(
        message
    )
}