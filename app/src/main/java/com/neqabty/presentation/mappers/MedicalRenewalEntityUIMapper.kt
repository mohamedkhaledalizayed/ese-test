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
                oldRefId = from.oldRefId,
                healthCareStatus = from.healthCareStatus,
                engineerStatus = from.engineerStatus,
                requestStatus = from.requestStatus,
                relations = from.relations?.map { return@map MedicalRenewalUI.RelationItem(id = it.id, name = it.name) },
                rejectionMsg = from.rejectionMsg,
                currentMedYear = from.currentMedYear,
                contact = from.contact?.let { return@let MedicalRenewalUI.ContactData(name = it.name, syndicateName = it.syndicateName, isDead = it.isDead, address = it.address, mobile = it.mobile, nationalId = it.nationalId, birthDate = it.birthDate, pic = it.pic, benID = it.benID) },
                followers = from.followers?.map { return@map MedicalRenewalUI.FollowerItem(name = it.name, id = it.id, isDeleted = it.isDeleted, modificationReason = it.modificationReason, birthDate = it.birthDate, pic = it.pic, mobile = it.mobile, nationalId = it.nationalId, relationType = it.relationType, relationTypeName = it.relationTypeName, gender = it.gender, lastMedYear = it.lastMedYear) }?.toMutableList()
        )
    }
}