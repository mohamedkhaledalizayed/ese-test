package com.neqabty.healthcare.commen.onboarding.contact.data.source

import com.neqabty.healthcare.commen.onboarding.contact.data.api.ContactApi
import com.neqabty.healthcare.commen.onboarding.contact.data.model.*
import com.neqabty.healthcare.commen.onboarding.contact.domain.entity.SubmitClientEntity
import com.neqabty.healthcare.core.data.PreferencesHelper
import okhttp3.MultipartBody
import javax.inject.Inject

class ContactDS @Inject constructor(private val contactApi: ContactApi, private val preferencesHelper: PreferencesHelper) {
    suspend fun checkMember(nationalId: String): CheckMemberResponse {
        return contactApi.checkMember(token = "token a1b2e1497425aa026d753e76b19db76bdd9a1f43", CheckMemberRequest(nationalId))
    }

    suspend fun createOCR(idFace: MultipartBody.Part?,
                          idBack: MultipartBody.Part?,
                          nationalId: String, mobile: String): CreateOCRResponse {
        return contactApi.createOCR(token = "token a1b2e1497425aa026d753e76b19db76bdd9a1f43", nationalId, mobile, idFace, idBack)
    }

    suspend fun getLookups(): List<GovResponse>{
        return contactApi.getLookups()
    }

    suspend fun submitClient(submitClientRequest: SubmitClientRequest): SubmitClientResponse {
        return contactApi.submitClient(token = "token a1b2e1497425aa026d753e76b19db76bdd9a1f43", submitClientRequest)
    }
}