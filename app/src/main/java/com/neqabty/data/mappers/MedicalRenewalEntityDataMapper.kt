package com.neqabty.data.mappers

import com.neqabty.data.entities.MedicalRenewalData
import com.neqabty.domain.common.Mapper
import com.neqabty.domain.entities.MedicalRenewalEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MedicalRenewalEntityDataMapper @Inject constructor() : Mapper<MedicalRenewalEntity, MedicalRenewalData>() {

    override fun mapFrom(from: MedicalRenewalEntity): MedicalRenewalData {
        return MedicalRenewalData(
                contact = from.contact?.let { return@let MedicalRenewalData.ContactData(name= it.name, contactID = it.contactID, syndicateName = it.syndicateName)},
                followers = from.followers?.map { return@map MedicalRenewalData.FollowerItem(name = it.name, id = it.id, isDeleted = it.isDeleted, birthDate = it.birthDate, pic = it.pic) }
        )
    }
}