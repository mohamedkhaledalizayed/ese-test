package com.neqabty.data.mappers

import com.neqabty.data.entities.CommitteesLookupData
import com.neqabty.domain.common.Mapper
import com.neqabty.domain.entities.CommitteesLookupEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CommitteesLookupsDataEntityMapper @Inject constructor() : Mapper<CommitteesLookupData, CommitteesLookupEntity>() {

    override fun mapFrom(from: CommitteesLookupData): CommitteesLookupEntity {
        return CommitteesLookupEntity(
            degreesList = from.degreesList,
            maritalStatusList = from.maritalStatusList,
            committeesList = from.committeesList.map { item -> CommitteesLookupEntity.CommitteeItem(item.id, item.name) },
            sectionsList = from.sectionsList.map { item -> CommitteesLookupEntity.SectionItem(item.id, item.name, item.nameEn) },
            syndicatesList = from.syndicatesList.map { item -> CommitteesLookupEntity.SyndicateItem(item.id, item.name, item.nameEn) }
        )
    }
}
