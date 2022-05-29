package com.neqabty.presentation.mappers

import com.neqabty.domain.common.Mapper
import com.neqabty.domain.entities.MedicalLetterEntity
import com.neqabty.presentation.entities.MedicalLetterUI
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MedicalLetterItemEntityUIMapper @Inject constructor() : Mapper<MedicalLetterEntity.LetterItem, MedicalLetterUI.LetterItem>() {

    override fun mapFrom(from: MedicalLetterEntity.LetterItem): MedicalLetterUI.LetterItem {
        val medicalLetterItemUI = MedicalLetterUI.LetterItem(
                id = from.id,
                name = from.name,
                letterTypeName = from.letterTypeName,
                isActive = from.isActive,
                letterDate = from.letterDate,
                letterStatusName = from.letterStatusName,
                serviceProviderName = from.serviceProviderName,
                totalPrice = from.totalPrice,
                creationType = from.creationType,
                report = from.report
        )
        from.letterDate?.let {
            val myFormat = "yyyy-MM-dd'T'HH:mm:ss" // In which you need put here
            val formattedDate = SimpleDateFormat(myFormat).parse(from.letterDate)
            val new = SimpleDateFormat("dd-MM-yyyy , HH:mm", Locale.ENGLISH).format(formattedDate.time)
            medicalLetterItemUI.letterDate = new.toString()
        }
        from.letterProcedures?.let {
            var letterProcedures: List<MedicalLetterUI.LetterProcedureItem> = it.map { procedureItem ->
                MedicalLetterUI.LetterProcedureItem(letterProcedureName = procedureItem.letterProcedureName)
            }

            medicalLetterItemUI.letterProcedures = letterProcedures

        }
        return medicalLetterItemUI
    }
}
