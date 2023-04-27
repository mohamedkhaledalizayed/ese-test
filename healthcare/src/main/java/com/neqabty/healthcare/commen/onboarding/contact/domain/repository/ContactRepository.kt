package com.neqabty.healthcare.commen.onboarding.contact.domain.repository

import com.neqabty.healthcare.commen.onboarding.contact.domain.entity.CheckMemberEntity
import com.neqabty.healthcare.commen.onboarding.contact.domain.entity.CreateOCREntity
import com.neqabty.healthcare.commen.onboarding.contact.domain.entity.GovEntity
import com.neqabty.healthcare.commen.onboarding.contact.domain.entity.SubmitClientEntity
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody

interface ContactRepository {
    fun checkMember(nationalId: String): Flow<CheckMemberEntity>
    fun createOCR(idFace: MultipartBody.Part?, idBack: MultipartBody.Part?, nationalId: String, mobile: String): Flow<CreateOCREntity>
    fun getLookups(): Flow<List<GovEntity>>
    fun submitClient(nationalId: String): Flow<SubmitClientEntity>
}