package com.neqabty.presentation.mappers

import com.neqabty.domain.common.Mapper
import com.neqabty.domain.entities.MedicalRenewalEntity
import com.neqabty.presentation.entities.MedicalRenewalUI
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MedicalRenewalEntityUIMapper @Inject constructor() : Mapper<MedicalRenewalEntity, MedicalRenewalUI>() {

    override fun mapFrom(from: MedicalRenewalEntity): MedicalRenewalUI {
        return MedicalRenewalUI(
                contact = from.contact?.let { return@let MedicalRenewalUI.ContactData(name= it.name, contactID = it.contactID, syndicateName = it.syndicateName)},
                followers = from.followers?.map { return@map MedicalRenewalUI.FollowerItem(name = it.name, id = it.id, isDeleted = it.isDeleted) }?.toMutableList()
        )
    }
}