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
                oldRefId = from.oldRefId,
                healthCareStatus = from.healthCareStatus,
                engineerStatus = from.engineerStatus,
                requestStatus = from.requestStatus,
                relations = from.relations?.map { return@map  MedicalRenewalData.RelationItem(id = it.id, name = it.name) },
                rejectionMsg = from.rejectionMsg,
                contact = from.contact?.let { return@let MedicalRenewalData.ContactData(name = it.name, syndicateName = it.syndicateName, isDead = it.isDead, address = it.address, mobile = it.mobile) },
                followers = from.followers?.map { return@map MedicalRenewalData.FollowerItem(name = it.name, id = it.id, isDeleted = it.isDeleted, birthDate = it.birthDate, pic = it.pic, attachments = it.attachments, mobile = it.mobile, nationalId = it.nationalId, relationType = it.relationType, gender = it.gender) }
        )
    }
}