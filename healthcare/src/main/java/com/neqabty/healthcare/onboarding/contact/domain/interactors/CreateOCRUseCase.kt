package com.neqabty.healthcare.onboarding.contact.domain.interactors

import com.neqabty.healthcare.onboarding.contact.domain.entity.CreateOCREntity
import com.neqabty.healthcare.onboarding.contact.domain.repository.ContactRepository
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import javax.inject.Inject

class CreateOCRUseCase @Inject constructor(private val repository: ContactRepository) {
    fun build(idFace: MultipartBody.Part?, idBack: MultipartBody.Part?, nationalId: String, mobile: String): Flow<CreateOCREntity> {
        return repository.createOCR(idFace, idBack, nationalId, mobile)
    }
}