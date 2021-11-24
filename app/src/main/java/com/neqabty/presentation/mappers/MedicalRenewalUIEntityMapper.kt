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
                healthCareStatus = from.healthCareStatus,
                engineerStatus = from.engineerStatus,
                requestStatus = from.requestStatus,
                relations = from.relations?.map { return@map  MedicalRenewalEntity.RelationItem(id = it.id, name = it.name) },
                rejectionMsg = from.rejectionMsg,
                currentMedYear = from.currentMedYear,
                contact = from.contact?.let { return@let MedicalRenewalEntity.ContactData(name = it.name, syndicateName = it.syndicateName, isDead = it.isDead, address = it.address, mobile = it.mobile) },
                followers = from.followers?.map { return@map MedicalRenewalEntity.FollowerItem(name = it.name, id = it.id, isDeleted = it.isDeleted, modificationReason = it.modificationReason, birthDate = it.birthDate, pic = it.pic, attachments = it.attachments, mobile = it.mobile, nationalId = it.nationalId, relationType = it.relationType, relationTypeName = it.relationTypeName, gender = it.gender) }?.toMutableList()
        )
    }
}