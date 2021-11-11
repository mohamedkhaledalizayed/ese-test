package com.neqabty.presentation.mappers

import com.neqabty.domain.common.Mapper
import com.neqabty.domain.entities.MedicalDirectoryLookupsEntity
import com.neqabty.presentation.entities.MedicalDirectoryLookupsUI
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MedicalDirectoryLookupsEntityUIMapper @Inject constructor() : Mapper<MedicalDirectoryLookupsEntity, MedicalDirectoryLookupsUI>() {

    override fun mapFrom(from: MedicalDirectoryLookupsEntity): MedicalDirectoryLookupsUI {
        var medicalDirectoryLookupsUI = MedicalDirectoryLookupsUI()

        from.providerTypes?.let {
            var providerTypes: List<MedicalDirectoryLookupsUI.ServiceProviderType> = it.map { providerItem ->
                return@map MedicalDirectoryLookupsUI.ServiceProviderType(
                    id = providerItem.id,
                    name = providerItem.name
                )
            }
            medicalDirectoryLookupsUI.providerTypes = providerTypes
        }

        from.governs?.let {
            var governs: List<MedicalDirectoryLookupsUI.Govern> = it.map { governItem ->
                return@map MedicalDirectoryLookupsUI.Govern(
                    id = governItem.id,
                    name = governItem.name
                )
            }
            medicalDirectoryLookupsUI.governs = governs
        }

        from.areas?.let {
            var areas: List<MedicalDirectoryLookupsUI.Area> = it.map { areaItem ->
                return@map MedicalDirectoryLookupsUI.Area(
                    id = areaItem.id,
                    name = areaItem.name,
                    govId = areaItem.govId
                )
            }
            medicalDirectoryLookupsUI.areas = areas
        }

        from.degrees?.let {
            var degrees: List<MedicalDirectoryLookupsUI.DoctorDegree> = it.map { degreeItem ->
                return@map MedicalDirectoryLookupsUI.DoctorDegree(
                    id = degreeItem.id,
                    name = degreeItem.name
                )
            }
            medicalDirectoryLookupsUI.degrees = degrees
        }

        from.specializations?.let {
            var specializations: List<MedicalDirectoryLookupsUI.DoctorSpecialization> = it.map { specializationItem ->
                return@map MedicalDirectoryLookupsUI.DoctorSpecialization(
                    id = specializationItem.id,
                    name = specializationItem.name
                )
            }
            medicalDirectoryLookupsUI.specializations = specializations
        }

        return medicalDirectoryLookupsUI
    }
}