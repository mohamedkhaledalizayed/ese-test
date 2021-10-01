package com.neqabty.presentation.mappers

import com.neqabty.domain.common.Mapper
import com.neqabty.domain.entities.MedicalLetterEntity
import com.neqabty.presentation.entities.MedicalLetterUI
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MedicalLetterEntityUIMapper @Inject constructor() : Mapper<MedicalLetterEntity, MedicalLetterUI>() {

    val medicalLetterItemEntityUIMapper = MedicalLetterItemEntityUIMapper()

    override fun mapFrom(from: MedicalLetterEntity): MedicalLetterUI {
        return MedicalLetterUI(
                totalCount = from.totalCount,
                letters = from.letters?.map { letterItem -> medicalLetterItemEntityUIMapper.mapFrom(letterItem) }?.toMutableList()
        )
    }
}
