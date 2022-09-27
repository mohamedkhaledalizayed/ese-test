package com.neqabty.presentation.mappers

import com.neqabty.domain.common.Mapper
import com.neqabty.domain.entities.CommitteesLookupEntity
import com.neqabty.presentation.entities.CommitteesLookupUI
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CommitteesLookupsEntityUIMapper @Inject constructor() : Mapper<CommitteesLookupEntity, CommitteesLookupUI>() {

    override fun mapFrom(from: CommitteesLookupEntity): CommitteesLookupUI {
        return CommitteesLookupUI(
            degreesList = from.degreesList,
            maritalStatusList = from.maritalStatusList,
            committeesList = from.committeesList.map { item -> CommitteesLookupUI.CommitteeItem(item.id, item.name) },
            sectionsList = from.sectionsList.map { item -> CommitteesLookupUI.SectionItem(item.id, item.name, item.nameEn) },
            syndicatesList = from.syndicatesList.map { item -> CommitteesLookupUI.SyndicateItem(item.id, item.name, item.nameEn) }
        )
    }
}
