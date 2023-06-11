package com.neqabty.healthcare.commen.onboarding.contact.data.repository


import com.neqabty.healthcare.commen.onboarding.contact.data.model.*
import com.neqabty.healthcare.commen.onboarding.contact.data.source.ContactDS
import com.neqabty.healthcare.commen.onboarding.contact.domain.entity.*
import com.neqabty.healthcare.commen.onboarding.contact.domain.entity.ExtractedInfo
import com.neqabty.healthcare.commen.onboarding.contact.domain.entity.Ocr
import com.neqabty.healthcare.commen.onboarding.contact.domain.entity.Result
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
    val ocrsList = ocrs.map { ocr ->
        val extractedInfo = ocr.result.extractedInfo
        val result = Result(
            ExtractedInfo(
                extractedInfo.gender ?: "",
                extractedInfo.religion ?: "",
                extractedInfo.correctDoc,
                extractedInfo.expiry_date ?: "",
                extractedInfo.national_id ?: "",
                extractedInfo.spouse_name ?: "",
                extractedInfo.profession_1 ?: "",
                extractedInfo.profession_2 ?: "",
                extractedInfo.release_date ?: "",
                extractedInfo.marital_status ?: "",
                extractedInfo.area ?: "",
                extractedInfo.street ?: "",
                extractedInfo.full_name ?: "",
                extractedInfo.birth_date ?: "",
                extractedInfo.first_name ?: "",
                extractedInfo.governorate ?: "",
                extractedInfo.serial_number ?: "",
                extractedInfo.serial_validation ?: ""
            ), ocr.result.completed
        )

        Ocr(
            ocr.id,
            ocr.created_at,
            ocr.updated_at,
            ocr.ocr_uuid,
            ocr.image_type,
            result,
            ocr.account
        )
    }

    return CheckMemberEntity(
        authorized, ocrStatus, message, ocrsList
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
        message.message.message.ar
    )
}