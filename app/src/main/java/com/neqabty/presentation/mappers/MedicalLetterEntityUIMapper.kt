package com.neqabty.presentation.mappers

import com.neqabty.domain.common.Mapper
import com.neqabty.domain.entities.MedicalLetterEntity
import com.neqabty.presentation.entities.MedicalLetterUI
import kotlinx.android.synthetic.main.medical_renew_add_follower_details_fragment.*
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MedicalLetterEntityUIMapper @Inject constructor() : Mapper<MedicalLetterEntity, MedicalLetterUI>() {

    override fun mapFrom(from: MedicalLetterEntity): MedicalLetterUI {
        val medicalLetterUI = MedicalLetterUI(
                totalCount = from.totalCount
        )

        from.letters?.let {
            var letters: List<MedicalLetterUI.LetterItem> = it.map { letterItem ->
                val myFormat = "yyyy-MM-dd'T'HH:mm:ss" // In which you need put here
                val formattedDate = SimpleDateFormat(myFormat).parse(letterItem.letterDate)
                val new = SimpleDateFormat("dd-MM-yyyy , HH:mm", Locale.ENGLISH).format(formattedDate.time)

                val letter = MedicalLetterUI.LetterItem(
                        letterTypeName = letterItem.letterTypeName,
                        isActive = letterItem.isActive,
                        letterDate = new.toString(),
                        letterStatusName = letterItem.letterStatusName,
                        serviceProviderName = letterItem.serviceProviderName,
                        totalPrice = letterItem.totalPrice,
                        creationType = letterItem.creationType
                )

                letterItem.letterProcedures?.let {
                    var letterProcedures: List<MedicalLetterUI.LetterProcedureItem> = it.map { procedureItem ->
                        MedicalLetterUI.LetterProcedureItem(letterProcedureName = procedureItem.letterProcedureName)
                    }

                letter.letterProcedures = letterProcedures
                }

                return@map letter
            }
            medicalLetterUI.letters = letters.toMutableList()
        }
        return medicalLetterUI
    }
}
