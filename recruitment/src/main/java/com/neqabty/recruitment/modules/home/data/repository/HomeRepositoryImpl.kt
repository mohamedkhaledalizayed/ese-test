package com.neqabty.recruitment.modules.home.data.repository

import com.neqabty.recruitment.modules.home.data.model.ads.AdModel
import com.neqabty.recruitment.modules.home.data.model.ads.CompanyModel
import com.neqabty.recruitment.modules.home.data.source.HomeDS
import com.neqabty.recruitment.modules.home.domain.entity.ads.AdEntity
import com.neqabty.recruitment.modules.home.domain.entity.ads.Company
import com.neqabty.recruitment.modules.home.domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(private val homeDS: HomeDS): HomeRepository {
    override fun getAds(): Flow<List<AdEntity>> {
        return flow {
            emit(homeDS.getAds().map { it.toAdEntity() })
        }
    }
}

private fun AdModel.toAdEntity(): AdEntity{
    return AdEntity(
        company = company.toCompany(),
        content = content,
        id = id,
        imageFile = imageFile,
        title = title
    )
}

private fun CompanyModel.toCompany(): Company{
    return Company(
        about = about,
        email = email,
        faxNumber = faxNumber,
        headquarters = headquarters,
        linkedInLink = linkedInLink,
        mobileNumber = mobileNumber,
        name = name,
        numberOfEmployees = numberOfEmployees,
        ownerName = ownerName,
        phoneNumber = phoneNumber,
        website = website
    )
}