package com.neqabty.meganeqabty.profile.data.repository


import com.neqabty.meganeqabty.profile.data.model.ministrylicence.MinistryLicenseModel
import com.neqabty.meganeqabty.profile.data.model.profile.AccountModel
import com.neqabty.meganeqabty.profile.data.model.profile.EntityModel
import com.neqabty.meganeqabty.profile.data.model.profile.ProfileModel
import com.neqabty.meganeqabty.profile.data.source.ProfileDS
import com.neqabty.meganeqabty.profile.domain.entity.MinistryLicenseEntity
import com.neqabty.meganeqabty.profile.domain.entity.profile.Account
import com.neqabty.meganeqabty.profile.domain.entity.profile.Entity
import com.neqabty.meganeqabty.profile.domain.entity.profile.ProfileEntity
import com.neqabty.meganeqabty.profile.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MultipartBody
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(private val profileDS: ProfileDS): ProfileRepository {

    override fun getUserProfile(token: String): Flow<ProfileEntity> {
        return flow {
            emit(profileDS.getUserProfile(token).toProfileEntity())
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

private fun MinistryLicenseModel.toMinistryLicenseEntity(): MinistryLicenseEntity{
    return MinistryLicenseEntity(
        id = id,
        license = license,
        member = member.id,
        status = status
    )
}

private fun ProfileModel.toProfileEntity(): ProfileEntity{
    return ProfileEntity(
        account = account.toAccount(),
        entity = entity.toEntity(),
        id = id,
        lastFeeYear = lastFeeYear,
        licenseEndDate = licenseEndDate,
        membershipId = membershipId,
        name = name,
        nationalId = nationalId
    )
}

private fun AccountModel.toAccount(): Account{
    return Account(
        email = email,
        fullname = fullname,
        id = id,
        image = image,
        mobile = mobile,
        nationalId = nationalId
    )
}

private fun EntityModel.toEntity(): Entity{
    return Entity(
        code = code,
        id = id,
        name = name
    )
}