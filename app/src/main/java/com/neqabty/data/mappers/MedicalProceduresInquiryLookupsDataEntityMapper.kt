package com.neqabty.data.mappers

import com.neqabty.data.entities.MedicalProceduresInquiryLookupsData
import com.neqabty.domain.common.Mapper
import com.neqabty.domain.entities.MedicalProceduresInquiryLookupsEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MedicalProceduresInquiryLookupsDataEntityMapper @Inject constructor() : Mapper<MedicalProceduresInquiryLookupsData, MedicalProceduresInquiryLookupsEntity>() {

    override fun mapFrom(from: MedicalProceduresInquiryLookupsData): MedicalProceduresInquiryLookupsEntity {
        var medicalInquiryLookupsEntity = MedicalProceduresInquiryLookupsEntity()

        from.governs?.let {
            var governs: List<MedicalProceduresInquiryLookupsEntity.Govern> = it.map { governItem ->
                return@map MedicalProceduresInquiryLookupsEntity.Govern(
                    id = governItem.id,
                    name = governItem.name ?: ""
                )
            }
            medicalInquiryLookupsEntity.governs = governs
        }

        from.areas?.let {
            var areas: List<MedicalProceduresInquiryLookupsEntity.Area> = it.map { areaItem ->
                return@map MedicalProceduresInquiryLookupsEntity.Area(
                    id = areaItem.id,
                    name = areaItem.name ?: "",
                    govId = areaItem.govId
                )
            }
            medicalInquiryLookupsEntity.areas = areas
        }

        from.categories?.let {
            var categories: List<MedicalProceduresInquiryLookupsEntity.ProcedureCategory> = it.map { categoryItem ->
                return@map MedicalProceduresInquiryLookupsEntity.ProcedureCategory(
                    id = categoryItem.id,
                    name = categoryItem.name,
                    parentCategoryId = categoryItem.parentCategoryId
                )
            }
            medicalInquiryLookupsEntity.categories = categories
        }

        from.relationTypes?.let {
            var relationTypes: List<MedicalProceduresInquiryLookupsEntity.RelationType> = it.map { relationTypeItem ->
                return@map MedicalProceduresInquiryLookupsEntity.RelationType(
                    id = relationTypeItem.id,
                    name = relationTypeItem.name ?: ""
                )
            }
            medicalInquiryLookupsEntity.relationTypes = relationTypes
        }

        return medicalInquiryLookupsEntity
    }
}