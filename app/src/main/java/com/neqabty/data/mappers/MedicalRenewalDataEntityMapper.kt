package com.neqabty.data.mappers

import com.neqabty.data.entities.MedicalRenewalData
import com.neqabty.domain.common.Mapper
import com.neqabty.domain.entities.MedicalRenewalEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MedicalRenewalDataEntityMapper @Inject constructor() : Mapper<MedicalRenewalData, MedicalRenewalEntity>() {

    override fun mapFrom(from: MedicalRenewalData): MedicalRenewalEntity {
        return MedicalRenewalEntity(
                oldRefId = from.oldRefId,
                healthCareStatus = from.healthCareStatus,
                engineerStatus = from.engineerStatus,
                requestStatus = from.requestStatus,
                relations = from.relations?.map { return@map MedicalRenewalEntity.RelationItem(id = it.id, name = it.name) },
                rejectionMsg = from.rejectionMsg,
                contact = from.contact?.let { return@let MedicalRenewalEntity.ContactData(name = it.name, syndicateName = it.syndicateName, isDead = it.isDead, address = it.address, mobile = it.mobile, nationalId = it.nationalId, birthDate = it.birthDate, pic = it.pic, benID = it.benID) },
                followers = from.followers?.map { return@map MedicalRenewalEntity.FollowerItem(name = it.name, id = it.id, isDeleted = it.isDeleted, birthDate = it.birthDate, pic = it.pic, attachments = it.attachments, mobile = it.mobile, nationalId = it.nationalId, relationType = it.relationType, relationTypeName = it.relationTypeName, gender = it.gender, lastMedYear = it.lastMedYear) }
        )
    }
}