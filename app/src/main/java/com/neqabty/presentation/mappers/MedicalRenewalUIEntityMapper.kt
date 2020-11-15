package com.neqabty.presentation.mappers

import com.neqabty.domain.common.Mapper
import com.neqabty.domain.entities.MedicalRenewalEntity
import com.neqabty.presentation.entities.MedicalRenewalUI
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MedicalRenewalUIEntityMapper @Inject constructor() : Mapper<MedicalRenewalUI, MedicalRenewalEntity>() {

    override fun mapFrom(from: MedicalRenewalUI): MedicalRenewalEntity {
        return MedicalRenewalEntity(
                oldRefId = from.oldRefId,
                contact = from.contact?.let { return@let MedicalRenewalEntity.ContactData(name = it.name, syndicateName = it.syndicateName, isNew = it.isNew, requestStatus = it.requestStatus) },
                followers = from.followers?.map { return@map MedicalRenewalEntity.FollowerItem(name = it.name, id = it.id, isDeleted = it.isDeleted, birthDate = it.birthDate, pic = it.pic, attachments = it.attachments) }?.toMutableList()
        )
    }
}