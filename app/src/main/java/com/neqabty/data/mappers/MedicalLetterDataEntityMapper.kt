package com.neqabty.data.mappers

import com.neqabty.data.entities.MedicalLetterData
import com.neqabty.domain.common.Mapper
import com.neqabty.domain.entities.MedicalLetterEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MedicalLetterDataEntityMapper @Inject constructor() : Mapper<MedicalLetterData, MedicalLetterEntity>() {

    val medicalLetterItemDataEntityMapper = MedicalLetterItemDataEntityMapper()

    override fun mapFrom(from: MedicalLetterData): MedicalLetterEntity {
        return MedicalLetterEntity(
                totalCount = from.totalCount,
                letters = from.letters?.map { letterItem -> medicalLetterItemDataEntityMapper.mapFrom(letterItem)}
        )
    }
}
