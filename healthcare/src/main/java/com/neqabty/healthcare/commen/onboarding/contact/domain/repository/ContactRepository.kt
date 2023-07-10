package com.neqabty.healthcare.commen.onboarding.contact.domain.repository

import com.neqabty.healthcare.commen.onboarding.contact.data.model.SubmitClientRequest
import com.neqabty.healthcare.commen.onboarding.contact.domain.entity.*
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody

interface ContactRepository {
    fun checkMember(nationalId: String): Flow<CheckMemberEntity>
    fun createOCR(idFace: MultipartBody.Part?, idBack: MultipartBody.Part?, nationalId: String, mobile: String): Flow<CreateOCREntity>
    fun getLookups(): Flow<List<GovEntity>>
    fun submitClient(submitClientRequest: SubmitClientRequest): Flow<SubmitClientEntity>
    fun getInstallments(nationalId: String, amount: String, tenor: String): Flow<InstallmentsEntity>
    fun submitInvoice(nationalId: String, amount: String, tenor: String): Flow<InvoiceEntity>
}