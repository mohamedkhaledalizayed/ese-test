package com.neqabty.healthcare.commen.onboarding.contact.data.source

import com.neqabty.healthcare.commen.onboarding.contact.data.api.ContactApi
import com.neqabty.healthcare.commen.onboarding.contact.data.model.*
import com.neqabty.healthcare.core.data.PreferencesHelper
import okhttp3.MultipartBody
import javax.inject.Inject

class ContactDS @Inject constructor(private val contactApi: ContactApi, private val preferencesHelper: PreferencesHelper) {
    suspend fun checkMember(nationalId: String): CheckMemberResponse {
        return contactApi.checkMember(token = "token ${preferencesHelper.token}", CheckMemberRequest(nationalId))
    }

    suspend fun createOCR(idFace: MultipartBody.Part?,
                          idBack: MultipartBody.Part?,
                          nationalId: String, mobile: String): CreateOCRResponse {
        return contactApi.createOCR(token = "token ${preferencesHelper.token}", CreateOCRRequest(idFace, idBack, nationalId, mobile))
    }

    suspend fun getLookups(): List<GovResponse>{
        return contactApi.getLookups()
    }

    suspend fun submitClient(nationalId: String): SubmitClientResponse {
        return contactApi.submitClient(token = "token ${preferencesHelper.token}", SubmitClientRequest(nationalId))
    }
}