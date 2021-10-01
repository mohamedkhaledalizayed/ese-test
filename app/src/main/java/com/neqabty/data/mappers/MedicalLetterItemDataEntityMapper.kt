package com.neqabty.data.mappers

import com.neqabty.data.entities.MedicalLetterData
import com.neqabty.domain.common.Mapper
import com.neqabty.domain.entities.MedicalLetterEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MedicalLetterItemDataEntityMapper @Inject constructor() : Mapper<MedicalLetterData.LetterItem, MedicalLetterEntity.LetterItem>() {

    override fun mapFrom(from: MedicalLetterData.LetterItem): MedicalLetterEntity.LetterItem {
        val medicalLetterItemEntity = MedicalLetterEntity.LetterItem(
                id = from.id,
                name = from.name,
                letterTypeName = from.letterTypeName,
                isActive = from.isActive,
                letterDate = from.letterDate,
                letterStatusName = from.letterStatusName,
                serviceProviderName = from.serviceProviderName,
                totalPrice = from.totalPrice,
                creationType = from.creationType
        )
        from.letterProcedures?.let {
            var letterProcedures: List<MedicalLetterEntity.LetterProcedureItem> = it.map { procedureItem ->
                MedicalLetterEntity.LetterProcedureItem(letterProcedureName = procedureItem.letterProcedureName)
            }

            medicalLetterItemEntity.letterProcedures = letterProcedures
        }

        return medicalLetterItemEntity
    }
}
