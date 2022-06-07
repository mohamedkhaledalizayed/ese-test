package com.neqabty.presentation.mappers

import com.neqabty.domain.common.Mapper
import com.neqabty.domain.entities.MedicalProceduresInquiryLookupsEntity
import com.neqabty.presentation.entities.MedicalProceduresInquiryLookupsUI
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MedicalProceduresInquiryLookupsEntityUIMapper @Inject constructor() : Mapper<MedicalProceduresInquiryLookupsEntity, MedicalProceduresInquiryLookupsUI>() {

    override fun mapFrom(from: MedicalProceduresInquiryLookupsEntity): MedicalProceduresInquiryLookupsUI {
        var medicalInquiryLookupsUI = MedicalProceduresInquiryLookupsUI()

        from.governs?.let {
            var governs: List<MedicalProceduresInquiryLookupsUI.Govern> = it.map { governItem ->
                return@map MedicalProceduresInquiryLookupsUI.Govern(
                    id = governItem.id,
                    name = governItem.name ?: ""
                )
            }
            medicalInquiryLookupsUI.governs = governs
        }

        from.areas?.let {
            var areas: List<MedicalProceduresInquiryLookupsUI.Area> = it.map { areaItem ->
                return@map MedicalProceduresInquiryLookupsUI.Area(
                    id = areaItem.id,
                    name = areaItem.name ?: "",
                    govId = areaItem.govId
                )
            }
            medicalInquiryLookupsUI.areas = areas
        }

        from.categories?.let {
            var categories: List<MedicalProceduresInquiryLookupsUI.ProcedureCategory> = it.map { categoryItem ->
                return@map MedicalProceduresInquiryLookupsUI.ProcedureCategory(
                    id = categoryItem.id,
                    name = categoryItem.name,
                    parentCategoryId = categoryItem.parentCategoryId ?: -1
                )
            }
            medicalInquiryLookupsUI.categories = categories
        }

        from.relationTypes?.let {
            var relationTypes: List<MedicalProceduresInquiryLookupsUI.RelationType> = it.map { relationTypeItem ->
                return@map MedicalProceduresInquiryLookupsUI.RelationType(
                    id = relationTypeItem.id,
                    name = relationTypeItem.name ?: ""
                )
            }
            medicalInquiryLookupsUI.relationTypes = relationTypes
        }

        return medicalInquiryLookupsUI
    }
}