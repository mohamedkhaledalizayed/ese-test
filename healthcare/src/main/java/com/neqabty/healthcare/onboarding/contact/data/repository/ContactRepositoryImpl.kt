package com.neqabty.healthcare.onboarding.contact.data.repository


import com.neqabty.healthcare.onboarding.contact.data.model.*
import com.neqabty.healthcare.onboarding.contact.data.source.ContactDS
import com.neqabty.healthcare.onboarding.contact.domain.entity.*
import com.neqabty.healthcare.onboarding.contact.domain.entity.ClientInfo
import com.neqabty.healthcare.onboarding.contact.domain.entity.ExtractedInfo
import com.neqabty.healthcare.onboarding.contact.domain.entity.Ocr
import com.neqabty.healthcare.onboarding.contact.domain.entity.Result
import com.neqabty.healthcare.onboarding.contact.domain.repository.ContactRepository
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

    override fun getInstallments(
        nationalId: String,
        amount: String,
        tenor: String
    ): Flow<InstallmentsEntity> {
        return flow {
            emit(contactDS.getInstallments(nationalId, amount, tenor).toInstallmentsEntity())
        }
    }

    override fun submitInvoice(
        nationalId: String,
        amount: String,
        tenor: String
    ): Flow<InvoiceEntity> {
        return flow {
            emit(contactDS.submitInvoice(nationalId, amount, tenor).toInvoiceEntity())
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
        authorized, ocrStatus, status, message, ocrsList,
        clientInfo?.let { ClientInfo(it.name, it.limit, it.availableBalance, it.nextDueDate, it.nextDueAmount) }
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
        success
    )
}

private fun InstallmentsResponse.toInstallmentsEntity(): InstallmentsEntity {
    return InstallmentsEntity(
        tenor, maxTenor, instalmentValue
    )
}

private fun InvoiceResponse.toInvoiceEntity(): InvoiceEntity {
    return InvoiceEntity(
        tenor, maxTenor, instalmentValue
    )
}