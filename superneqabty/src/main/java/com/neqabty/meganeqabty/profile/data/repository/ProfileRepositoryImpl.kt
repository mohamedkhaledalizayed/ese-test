package com.neqabty.meganeqabty.profile.data.repository


import com.neqabty.meganeqabty.profile.data.model.licencestatus.LicenceStatusModel
import com.neqabty.meganeqabty.profile.data.model.membershipcardstatus.MemberShipCardStatus
import com.neqabty.meganeqabty.profile.data.model.ministrylicence.MinistryLicenseModel
import com.neqabty.meganeqabty.profile.data.model.profile.EntityBody
import com.neqabty.meganeqabty.profile.data.model.profile.ProfileModel
import com.neqabty.meganeqabty.profile.data.model.profile.UserData
import com.neqabty.meganeqabty.profile.data.source.ProfileDS
import com.neqabty.meganeqabty.profile.domain.entity.MinistryLicenseEntity
import com.neqabty.meganeqabty.profile.domain.entity.licencestatus.LicenceStatusEntity
import com.neqabty.meganeqabty.profile.domain.entity.membershipcardstatus.CardStatusEntity
import com.neqabty.meganeqabty.profile.domain.entity.profile.Data
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

    override fun membershipCardStatus(): Flow<CardStatusEntity> {
        return flow {
            emit(profileDS.membershipCardStatus().toCardStatusEntity())
        }
    }

    override fun licenseStatus(): Flow<LicenceStatusEntity> {
        return flow {
            emit(profileDS.licenseStatus().toLicenceStatusEntity())
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
        data = data.toData(),
        message = message,
        status = status
    )
}

private fun UserData.toData(): Data{
    return Data(
        email = account.email,
        entity = entity.toEntity(),
        fullName = account.fullname,
        id = id,
        image = account.image,
        mobile = account.mobile,
        nationalId = nationalId
    )
}

private fun EntityBody.toEntity(): Entity {
    return Entity(
        code = code,
        id = id,
        name = name
    )
}

private fun MemberShipCardStatus.toCardStatusEntity(): CardStatusEntity{
    return CardStatusEntity(
        syndicateName = member.entity,
        address = address,
        mobile = mobile,
        statusCode = statusCode,
        statusMessage = statusMessage,
        photo = photo,
        year = cardYear.toString(),
        delivered = delivered
    )
}

private fun LicenceStatusModel.toLicenceStatusEntity(): LicenceStatusEntity{
    return LicenceStatusEntity(
        syndicateName = member.entity,
        statusCode = statusCode,
        statusMessage = statusMessage,
        photo = license,
        delivered = delivered
    )
}