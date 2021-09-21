package com.neqabty.data.mappers

import com.neqabty.data.entities.MedicalLetterData
import com.neqabty.domain.common.Mapper
import com.neqabty.domain.entities.MedicalLetterEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MedicalLetterDataEntityMapper @Inject constructor() : Mapper<MedicalLetterData, MedicalLetterEntity>() {

    override fun mapFrom(from: MedicalLetterData): MedicalLetterEntity {
        val medicalLetterEntity = MedicalLetterEntity(
                totalCount = from.totalCount
        )

        from.letters?.let {
            var letters: List<MedicalLetterEntity.LetterItem> = it.map { letterItem ->
                val letter = MedicalLetterEntity.LetterItem(
                        letterTypeName = letterItem.letterTypeName,
                        isActive = letterItem.isActive,
                        letterDate = letterItem.letterDate,
                        letterStatusName = letterItem.letterStatusName,
                        serviceProviderName = letterItem.serviceProviderName,
                        totalPrice = letterItem.totalPrice,
                        creationType = letterItem.creationType
                )

                letterItem.letterProcedures?.let {
                    var letterProcedures: List<MedicalLetterEntity.LetterProcedureItem> = it.map { procedureItem ->
                        MedicalLetterEntity.LetterProcedureItem(letterProcedureName = procedureItem.letterProcedureName)
                    }

                letter.letterProcedures = letterProcedures
                }

                return@map letter
            }
            medicalLetterEntity.letters = letters
        }
        return medicalLetterEntity
    }
}
