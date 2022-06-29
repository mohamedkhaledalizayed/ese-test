package com.neqabty.meganeqabty.profile.data.repository


import com.neqabty.meganeqabty.profile.data.model.ministrylicence.MinistryLicenseModel
import com.neqabty.meganeqabty.profile.data.source.ProfileDS
import com.neqabty.meganeqabty.profile.domain.entity.MinistryLicenseEntity
import com.neqabty.meganeqabty.profile.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MultipartBody
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(private val profileDS: ProfileDS): ProfileRepository {

    override fun getUserProfile(token: String): Flow<String> {
        return flow {
            emit(profileDS.getUserProfile(token))
        }
    }

    override fun uploadMembershipCard(token: String, mobile: String, address: String, year: Int, photo: MultipartBody.Part?): Flow<String> {
        return flow {
            emit(profileDS.uploadMembershipCard(token, mobile, address, year, photo))
        }
    }

    override fun uploadMinistryLicense(token: String, license: MultipartBody.Part?): Flow<MinistryLicenseEntity> {
        return flow {
            emit(profileDS.uploadMinistryLicense(token, license).toMinistryLicenseEntity())
        }
    }

}

fun MinistryLicenseModel.toMinistryLicenseEntity(): MinistryLicenseEntity{
    return MinistryLicenseEntity(
        id = id,
        license = license,
        member = member.id,
        status = status
    )
}